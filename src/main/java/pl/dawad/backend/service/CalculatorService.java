package pl.dawad.backend.service;

import org.springframework.stereotype.Service;
import pl.dawad.backend.enums.*;
import pl.dawad.backend.model.entity.*;
import pl.dawad.backend.service.database.CalculationDataSettingsService;
import pl.dawad.backend.service.database.EnergyStorageService;
import pl.dawad.backend.service.database.PhotovoltaicItemService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Service
public class CalculatorService {
    private final PhotovoltaicItemService photovoltaicItemService;
    private final CalculationDataSettingsService calculationDataSettingsService;
    private final EnergyStorageService energyStorageService;
    private final int calculationScale = 2;
    private final RoundingMode roundingMode = RoundingMode.HALF_UP;
    private final double PRIVATE_VAT = 1.08;
    private final double BUSINESS_VAT = 1.00;

    public CalculatorService(PhotovoltaicItemService photovoltaicItemService, CalculationDataSettingsService calculationDataSettingsService, EnergyStorageService energyStorageService) {
        this.photovoltaicItemService = photovoltaicItemService;
        this.calculationDataSettingsService = calculationDataSettingsService;
        this.energyStorageService = energyStorageService;
    }

    public CalculationResult processCalculation(CalculationFormData calculationFormData) {
        CalculationDataSettings calculationDataSettings = calculationDataSettingsService.getSettings();

        PhotovoltaicItem photovoltaicItem = photovoltaicItemService.getClosestPhotovoltaicItemByPower(determinePvPowerToFind(calculationFormData));
        BigDecimal corePrice = calculateCorePrice(photovoltaicItem, calculationFormData, calculationDataSettings,true);
        BigDecimal corePriceWithoutGrant = calculateCorePrice(photovoltaicItem, calculationFormData, calculationDataSettings,false);

        return createCalculationResult(calculationFormData, photovoltaicItem, corePrice, corePriceWithoutGrant, calculationDataSettings);
    }

    private BigDecimal calculateCorePrice(PhotovoltaicItem pvItem,
                                          CalculationFormData formData,
                                          CalculationDataSettings settings,
                                          boolean includeGrant) {
        BigDecimal basePrice = determinePvItemCorePrice(pvItem, formData.getInstallationType(), formData.getRoofType(), formData.getRoofSurface());
        return calculatePriceWithAdditionalOptions(settings, pvItem, formData, basePrice, includeGrant);
    }

    private CalculationResult createCalculationResult(CalculationFormData formData,
                                                      PhotovoltaicItem pvItem,
                                                      BigDecimal corePrice,
                                                      BigDecimal corePriceWithoutGrant,
                                                      CalculationDataSettings settings) {
        BigDecimal grossMargin = settings.getGrossMargin();
        double vatTax = determineVatTax(formData.getCustomerType());
        BigDecimal resultPrice = applyGrossMarginAndVat(grossMargin, corePrice, vatTax);
        BigDecimal resultPriceWithoutGrant = applyGrossMarginAndVat(grossMargin, corePriceWithoutGrant, vatTax);

        CalculationResult calculationResult = new CalculationResult();
        calculationResult.setCalculationDate(LocalDateTime.now(ZoneId.of("Europe/Warsaw")));
        calculationResult.setProposedPvPower(pvItem.getPvPower());
        calculationResult.setEstimatedOneYearProduction(calculateEstimatedOneYearProduction(settings.getMarketEnergyPricePerKwh(), pvItem.getPvPower()));
        calculationResult.setInverterModel(pvItem.getInverterModel());
        calculationResult.setMountTypeForView(determineMountType(formData.getInstallationType(), formData.getRoofType()).getDisplayedName());
        calculationResult.setPrice(resultPrice);
        calculationResult.setPriceWithoutGrant(resultPriceWithoutGrant);
        calculationResult.setModuleModel(pvItem.getModuleModel());
        calculationResult.setModulePower(pvItem.getModulePower());
        calculationResult.setPanelsQuantity(pvItem.getPanelsQuantity());
        calculationResult.setVatTax(vatTax);
        calculationResult.setPricePerKw(resultPrice.divide(pvItem.getPvPower(), calculationScale, roundingMode));
        calculationResult.setEnergyPricePerKwh(settings.getMarketEnergyPricePerKwh());
        calculationResult.setProjoyIncluded((pvItem.getCorePriceProjoy().compareTo(BigDecimal.ZERO)) <= 0);
        calculationResult.setGrantPossible(isGrantPossible(pvItem,settings,formData));
        calculationResult.setEnergyStorageAvailable(pvItem.isEnergyStorageAvailable());
        return calculationResult;
    }

    private BigDecimal applyGrossMarginAndVat(BigDecimal grossMargin, BigDecimal corePrice, double vatTax) {
        return grossMargin.multiply(corePrice).multiply(BigDecimal.valueOf(vatTax));
    }


    private BigDecimal calculatePriceWithAdditionalOptions(CalculationDataSettings settings,
                                                           PhotovoltaicItem pvItem,
                                                           CalculationFormData formData,
                                                           BigDecimal corePrice,
                                                           boolean includeGrant) {
        PowerOptimizersType powerOptimizersType = formData.getPowerOptimizersType();

        if (formData.isProjoy()) {
            corePrice = corePrice.add(pvItem.getCorePriceProjoy());
        }
        if (formData.isFireButton()) {
            corePrice = corePrice.add(pvItem.getCorePriceFireButton());
        }

        if (powerOptimizersType != null) {
            int powerOptimizers = switch (powerOptimizersType) {
                case ALL_MODULES -> pvItem.getPanelsQuantity();
                case HALF_MODULES -> Math.floorDiv(pvItem.getPanelsQuantity(), 2);
            };

            corePrice = corePrice.add(
                    settings.getPowerOptimizerCorePrice().multiply(
                            BigDecimal.valueOf(powerOptimizers)));
        }

        if (pvItem.isEnergyStorageAvailable()
                && formData.getEnergyStorageModelId() != null
                && formData.getEnergyStorageModelId() > 0) {
            Long energyStorageModel = formData.getEnergyStorageModelId();
            EnergyStorage energyStorage = energyStorageService.getEnergyStorageById(energyStorageModel);
            corePrice = corePrice.add(energyStorage.getCorePrice());
            if (includeGrant
                    && formData.isGrant()
                    && settings.isGrantAvailable()
                    && isGrantPossible(pvItem,settings,formData)) {
                corePrice = applyGrantToPrice(settings, corePrice);
            }
        }

        return corePrice;
    }

    private boolean isGrantPossible(PhotovoltaicItem pvItem,
                                    CalculationDataSettings calculationDataSettings,
                                    CalculationFormData calculationFormData){
        if(calculationFormData.getCustomerType() == CustomerType.ENTREPRENEUR) {
            return false;
        }
        return pvItem.getPvPower().compareTo(calculationDataSettings.getMaxPvPowerForGrant()) <= 0;
    }

    private BigDecimal applyGrantToPrice(CalculationDataSettings calculationDataSettings,
                                               BigDecimal corePrice) {
        BigDecimal grantValue = calculationDataSettings.getGrantValue();

        if (grantValue == null) {
            return corePrice;
        }

        BigDecimal halfCorePrice = corePrice.divide(BigDecimal.TWO, roundingMode);
        BigDecimal grantToApply = grantValue.compareTo(halfCorePrice) <= 0 ? grantValue : halfCorePrice;

        return corePrice.subtract(grantToApply);
    }

    private BigDecimal calculateEstimatedOneYearProduction(BigDecimal marketEnergyPricePerKwh, BigDecimal proposedPvPower) {
        BigDecimal production = proposedPvPower.multiply(BigDecimal.valueOf(1000)).multiply(marketEnergyPricePerKwh);
        return production.divide(BigDecimal.valueOf(50), 0, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(50));
    }


    private double determineVatTax(CustomerType customerType) {
        return Objects.requireNonNull(customerType) == CustomerType.PRIVATE ? PRIVATE_VAT : BUSINESS_VAT;
    }

    private BigDecimal determinePvItemCorePrice(PhotovoltaicItem photovoltaicItem,
                                                InstallationType installationType,
                                                RoofType roofType,
                                                RoofSurface roofSurface) {
        return switch (installationType) {
            case GROUND -> photovoltaicItem.getCorePriceGround();
            case ROOF -> switch (roofType) {
                case FLAT_ROOF -> photovoltaicItem.getCorePriceBallastFlatRoof();
                case SLANT_ROOF -> switch (roofSurface) {
                    case STEEL_SLANT -> photovoltaicItem.getCorePriceSteelSlantRoof();
                    case CERAMIC_TILE_SLANT -> photovoltaicItem.getCorePriceCeramicTileSlantRoof();
                    case STEEL_TILE_SLANT -> photovoltaicItem.getCorePriceSteelTileSlantRoof();
                    default -> null;
                };
            };
        };
    }

    private MountTypeForView determineMountType(InstallationType installationType, RoofType roofType) {
        return switch (installationType) {
            case GROUND -> MountTypeForView.GROUND;
            case ROOF -> switch (roofType) {
                case FLAT_ROOF -> MountTypeForView.FLAT_ROOF;
                case SLANT_ROOF -> MountTypeForView.SLANT_ROOF;
            };
        };
    }

    private BigDecimal determinePvPowerToFind(CalculationFormData calculationFormData) {
        BigDecimal expectedPvPower = calculationFormData.getExpectedPvPower();
        if (expectedPvPower != null && expectedPvPower.compareTo(BigDecimal.ZERO) > 0) {
            return expectedPvPower;
        }
        return calculationFormData.getEnergyConsumptionPerYear()
                .multiply(BigDecimal.valueOf(1.3))
                .divide(BigDecimal.valueOf(1000), calculationScale, roundingMode);
    }
}


