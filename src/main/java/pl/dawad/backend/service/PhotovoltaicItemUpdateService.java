package pl.dawad.backend.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.dawad.backend.exception.ExcelImportException;
import pl.dawad.backend.model.entity.PhotovoltaicItem;
import pl.dawad.backend.model.PhotovoltaicItemImportParams;
import pl.dawad.backend.repository.PhotovoltaicItemRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PhotovoltaicItemUpdateService {
    private final PhotovoltaicItemRepository photovoltaicItemRepository;

    @Autowired
    public PhotovoltaicItemUpdateService(PhotovoltaicItemRepository photovoltaicItemRepository) {
        this.photovoltaicItemRepository = photovoltaicItemRepository;
    }

    public void importFromExcelFile(MultipartFile file, PhotovoltaicItemImportParams photovoltaicItemImportParams) {
        try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
            List<PhotovoltaicItem> items = parseExcelFile(workbook, photovoltaicItemImportParams);
            savePhotovoltaicItems(items);
        } catch (IOException e) {
            throw new ExcelImportException("Failed to read Excel file: " + e.getMessage());
        } catch (Exception e) {
            throw new ExcelImportException("Failed to import PhotovoltaicItems: " + e.getMessage());
        }
    }

    private List<PhotovoltaicItem> parseExcelFile(XSSFWorkbook workbook, PhotovoltaicItemImportParams importParams) {
        List<PhotovoltaicItem> items = new ArrayList<>();
        XSSFSheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() < importParams.getColumnsToIgnore()) {
                continue;
            }

            if (isRowEmpty(row)) {
                break;
            }

            PhotovoltaicItem item = buildPhotovoltaicItemFromRow(row, importParams);
            items.add(item);
        }

        return items;
    }

    private boolean isRowEmpty(Row row) {
        return row == null || row.getCell(0) == null || row.getCell(0).getCellType() == CellType.BLANK;
    }

    private PhotovoltaicItem buildPhotovoltaicItemFromRow(Row row, PhotovoltaicItemImportParams importParams) {
        PhotovoltaicItem item = new PhotovoltaicItem();
        item.setInverterModel(getStringValueFromCell(row.getCell(importParams.getInverterIndex())));
        item.setModuleModel(getStringValueFromCell(row.getCell(importParams.getModuleModelIndex())));
        item.setModulePower(getIntValueFromCell(row.getCell(importParams.getModulePowerIndex())));
        item.setPanelsQuantity(getNumericValueFromCell(row.getCell(importParams.getPanelsQuantityIndex())).orElse(0.0).intValue());
        item.setInverterPower(getBigDecimalValueFromCell(row.getCell(importParams.getInverterPowerIndex())));
        item.setPvPower(getBigDecimalValueFromCell(row.getCell(importParams.getPvPowerIndex())));
        item.setCorePriceCeramicTileSlantRoof(getBigDecimalValueFromCell(row.getCell(importParams.getCorePriceCeramicTileSlantRoofIndex())));
        item.setCorePriceSteelTileSlantRoof(getBigDecimalValueFromCell(row.getCell(importParams.getCorePriceSteelTileSlantRoofIndex())));
        item.setCorePriceSteelSlantRoof(getBigDecimalValueFromCell(row.getCell(importParams.getCorePriceSteelSlantRoofIndex())));
        item.setCorePriceBallastFlatRoof(getBigDecimalValueFromCell(row.getCell(importParams.getCorePriceBallastFlatRoofIndex())));
        item.setCorePriceInvasiveFlatRoof(getBigDecimalValueFromCell(row.getCell(importParams.getCorePriceInvasiveFlatRoofIndex())));
        item.setCorePriceGround(getBigDecimalValueFromCell(row.getCell(importParams.getCorePriceGroundIndex())));
        item.setCorePriceProjoy(getBigDecimalValueFromCell(row.getCell(importParams.getCorePriceProjoyIndex())));
        item.setCorePriceFireButton(getBigDecimalValueFromCell(row.getCell(importParams.getCorePriceFireButtonIndex())));
        item.setCorePriceHybridInverter(getBigDecimalValueFromCell(row.getCell(importParams.getCorePriceHybridInverterIndex())));
        item.setEnergyStorageAvailable(isValueNumeric(row.getCell(importParams.getCorePriceHybridInverterIndex())));
        return item;
    }
    private Optional<Double> getNumericValueFromCell(Cell cell) {
        if (cell != null && (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA)) {
            return Optional.of(cell.getNumericCellValue());
        }
        return Optional.empty();
    }

    private BigDecimal getBigDecimalValueFromCell(Cell cell) {
        try {
            if (cell != null) {
                return switch (cell.getCellType()) {
                    case NUMERIC, FORMULA -> BigDecimal.valueOf(cell.getNumericCellValue());
                    case STRING -> new BigDecimal(cell.getStringCellValue());
                    default -> BigDecimal.ZERO;
                };
            }
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.ZERO;
    }

    private int getIntValueFromCell(Cell cell) {
        return getNumericValueFromCell(cell).orElse(0.0).intValue();
    }

    private String getStringValueFromCell(Cell cell) {
        if (cell != null && cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        }
        return null;
    }

    private boolean isValueNumeric(Cell cell) {
        return getNumericValueFromCell(cell).isPresent();
    }

    public void savePhotovoltaicItems(List<PhotovoltaicItem> items) {
        photovoltaicItemRepository.saveAll(items);
    }
}
