package pl.dawad.backend.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.dawad.backend.model.PhotovoltaicItem;
import pl.dawad.backend.repository.PhotovoltaicItemRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PhotovoltaicItemUpdateService {
    private final PhotovoltaicItemRepository photovoltaicItemRepository;
    @Autowired
    public PhotovoltaicItemUpdateService(PhotovoltaicItemRepository photovoltaicItemRepository) {
        this.photovoltaicItemRepository = photovoltaicItemRepository;
    }
    public void importFromExcelFile(MultipartFile file, int columnsToIgnore,
                                    int inverterIndex, int panelsQuantityIndex,
                                    int panelsPowerIndex, int inverterPowerIndex,
                                    int pvPowerIndex, int corePriceBallastFlatRoofIndex,
                                    int corePriceInvasiveFlatRoofIndex, int corePriceCeramicTileSlantRoofIndex,
                                    int corePriceSteelTileSlantRoofIndex, int corePriceSteelSlantRoofIndex,
                                    int corePriceGroundIndex) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            List<PhotovoltaicItem> items = parseExcelFile(workbook, columnsToIgnore, inverterIndex,
                    panelsQuantityIndex, panelsPowerIndex,  inverterPowerIndex,
                    pvPowerIndex, corePriceBallastFlatRoofIndex, corePriceInvasiveFlatRoofIndex,
                    corePriceCeramicTileSlantRoofIndex, corePriceSteelTileSlantRoofIndex, corePriceSteelSlantRoofIndex,
                    corePriceGroundIndex);
            savePhotovoltaicItems(items);
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to import PhotovoltaicItems from Excel: " + e.getMessage(), e);
        }
    }

    private List<PhotovoltaicItem> parseExcelFile(XSSFWorkbook workbook, int columnsToIgnore,
                                                  int inverterIndex, int panelsQuantityIndex,
                                                  int panelsPowerIndex, int inverterPowerIndex,
                                                  int pvPowerIndex, int corePriceBallastFlatRoofIndex,
                                                  int corePriceInvasiveFlatRoofIndex, int corePriceCeramicTileSlantRoofIndex,
                                                  int corePriceSteelTileSlantRoofIndex, int corePriceSteelSlantRoofIndex,
                                                  int corePriceGroundIndex) {
        List<PhotovoltaicItem> items = new ArrayList<>();
        XSSFSheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() < columnsToIgnore) {
                continue;
            }
            if (row.getCell(0) == null || row.getCell(0).getCellType() == CellType.BLANK) {
                break;
            }
            PhotovoltaicItem item = new PhotovoltaicItem();
            item.setInverter(getStringValueFromCell(row.getCell(inverterIndex)));
            item.setInverterPower(getBigDecimalValueFromCell(row.getCell(inverterPowerIndex)));
            item.setPanelsPower(getBigDecimalValueFromCell(row.getCell(panelsPowerIndex)));
            item.setPanelsQuantity((int) row.getCell(panelsQuantityIndex).getNumericCellValue());
            item.setPvPower(getBigDecimalValueFromCell(row.getCell(pvPowerIndex)));
            item.setCorePriceCeramicTileSlantRoof(getBigDecimalValueFromCell(row.getCell(corePriceCeramicTileSlantRoofIndex)));
            item.setCorePriceSteelTileSlantRoof(getBigDecimalValueFromCell(row.getCell(corePriceSteelTileSlantRoofIndex)));
            item.setCorePriceSteelSlantRoof(getBigDecimalValueFromCell(row.getCell(corePriceSteelSlantRoofIndex)));
            item.setCorePriceBallastFlatRoof(getBigDecimalValueFromCell(row.getCell(corePriceBallastFlatRoofIndex)));
            item.setCorePriceInvasiveFlatRoof(getBigDecimalValueFromCell(row.getCell(corePriceInvasiveFlatRoofIndex)));
            item.setCorePriceGround(getBigDecimalValueFromCell(row.getCell(corePriceGroundIndex)));
            items.add(item);
        }

        return items;
    }

    private BigDecimal getBigDecimalValueFromCell(Cell cell) {
        BigDecimal value = null;
        if (cell != null) {
            if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
                value = BigDecimal.valueOf(cell.getNumericCellValue());
            } else if (cell.getCellType() == CellType.STRING) {
                value = new BigDecimal(cell.getStringCellValue());
            }
        }
        return value;
    }

    private String getStringValueFromCell(Cell cell) {
        return cell != null ? cell.getStringCellValue() : null;
    }

    public void savePhotovoltaicItems(List<PhotovoltaicItem> items) {
        photovoltaicItemRepository.saveAll(items);
    }
}
