package pl.dawad.backend.service;

import org.springframework.stereotype.Service;
import pl.dawad.backend.enums.MountType;
import pl.dawad.backend.enums.CustomerType;
import pl.dawad.backend.model.ContactForm;
import pl.dawad.backend.model.CalculationDataSettings;
import pl.dawad.backend.model.PhotovoltaicItem;
import pl.dawad.backend.validation.CustomValidator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Service
public class CalculatorService {
    private final CustomValidator customValidator;
    private final PhotovoltaicItemService photovoltaicItemService;
    private final CalculationDataSettingsService calculationDataSettingsService;

    public CalculatorService(CustomValidator customValidator, PhotovoltaicItemService photovoltaicItemService, CalculationDataSettingsService calculationDataSettingsService) {
        this.customValidator = customValidator;
        this.photovoltaicItemService = photovoltaicItemService;
        this.calculationDataSettingsService = calculationDataSettingsService;
    }

    public ContactForm processCalculation(ContactForm contactForm) {
        CalculationDataSettings calculationDataSettings;
        PhotovoltaicItem photovoltaicItem;
        BigDecimal grossMarginMin;
        BigDecimal grossMarginMax;
        BigDecimal resultPriceMin;
        BigDecimal resultPriceMax;
        BigDecimal expectedPvPower;
        BigDecimal energyConsumptionPerYear;
        BigDecimal pvPowerToFind;
        BigDecimal marketEnergyPricePerKwh;
        BigDecimal customerEnergyPricePerKwh;
        BigDecimal finalEnergyPricePerKwh;
        BigDecimal corePrice = null;
        BigDecimal proposedPvPower;
        BigDecimal vatTax = null;
        CustomerType customerType;

        MountType mountType;
        int calculationScale = 2;
        RoundingMode roundingMode = RoundingMode.HALF_UP;

        customValidator.validateContactFormForCalculation(contactForm);
        calculationDataSettings = calculationDataSettingsService.getSettings();
        customValidator.validateGrossMarginSettings(calculationDataSettings);

        grossMarginMin = calculationDataSettings.getGrossMarginFrom();
        grossMarginMax = calculationDataSettings.getGrossMarginTo();
        marketEnergyPricePerKwh = calculationDataSettings.getEnergyPricePerKwh();
        customerEnergyPricePerKwh = contactForm.getEnergyPricePerKwh();
        expectedPvPower = contactForm.getExpectedPvPower();
        energyConsumptionPerYear = contactForm.getEnergyConsumptionPerYear();
        mountType = contactForm.getMountType();
        customerType = contactForm.getCustomerType();

        if(customerEnergyPricePerKwh == null || customerEnergyPricePerKwh.equals(BigDecimal.ZERO)){
            finalEnergyPricePerKwh = marketEnergyPricePerKwh;
        } else {
            finalEnergyPricePerKwh = customerEnergyPricePerKwh;
        }

        if (expectedPvPower == null || expectedPvPower.compareTo(BigDecimal.ZERO) <= 0){
            pvPowerToFind = energyConsumptionPerYear
                    .multiply(BigDecimal.valueOf(1.3))
                    .divide(BigDecimal.valueOf(1000), calculationScale, roundingMode);
        } else {
            pvPowerToFind = expectedPvPower;
        }

        photovoltaicItem = photovoltaicItemService.getClosestPhotovoltaicItemByPower(pvPowerToFind);

        switch (mountType) {
            case CERAMIC_TILE_SLANT -> corePrice = photovoltaicItem.getCorePriceCeramicTileSlantRoof();
            case STEEL_TILE_SLANT -> corePrice = photovoltaicItem.getCorePriceSteelTileSlantRoof();
            case STEEL_SLANT -> corePrice = photovoltaicItem.getCorePriceSteelSlantRoof();
            case INVASIVE_FLAT -> corePrice = photovoltaicItem.getCorePriceInvasiveFlatRoof();
            case BALLAST_FLAT -> corePrice = photovoltaicItem.getCorePriceBallastFlatRoof();
            case GROUND -> corePrice = photovoltaicItem.getCorePriceGround();
        }

        if (Objects.requireNonNull(customerType) == CustomerType.PRIVATE) {
            vatTax = BigDecimal.valueOf(1.08);
        } else if (customerType == CustomerType.ENTREPRENEUR) {
            vatTax = BigDecimal.valueOf(1.00);
        }

        resultPriceMin = grossMarginMin.multiply(corePrice);
        resultPriceMax = grossMarginMax.multiply(corePrice);
        proposedPvPower = photovoltaicItem.getPvPower();

        resultPriceMin = resultPriceMin.multiply(vatTax);
        resultPriceMax = resultPriceMax.multiply(vatTax);

        contactForm.setInverter(photovoltaicItem.getInverter());
        contactForm.setPriceFrom(resultPriceMin);
        contactForm.setPriceTo(resultPriceMax);
        contactForm.setProposedPvPower(proposedPvPower);
        contactForm.setVatTax(vatTax);
        contactForm.setPricePerKw(resultPriceMin.divide(proposedPvPower, calculationScale,roundingMode));

        if (energyConsumptionPerYear != null) {
            contactForm.setInvestmentReturnInYears(
                    resultPriceMin
                            .divide(proposedPvPower
                                    .multiply(BigDecimal.valueOf(1000))
                                    .multiply(finalEnergyPricePerKwh), calculationScale, roundingMode));
        }

        return contactForm;
    }
}
