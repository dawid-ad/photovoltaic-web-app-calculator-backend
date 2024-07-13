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
import pl.dawad.backend.model.PhotovoltaicItem;
import pl.dawad.backend.model.PhotovoltaicItemImportParams;
import pl.dawad.backend.repository.PhotovoltaicItemRepository;

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

    public void importFromExcelFile(MultipartFile file, PhotovoltaicItemImportParams photovoltaicItemImportParams) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            List<PhotovoltaicItem> items = parseExcelFile(workbook, photovoltaicItemImportParams);
            savePhotovoltaicItems(items);
            workbook.close();
        } catch (Exception e) {
            throw new ExcelImportException("Failed to import PhotovoltaicItems from Excel: " + e.getMessage());
        }
    }

    private List<PhotovoltaicItem> parseExcelFile(XSSFWorkbook workbook, PhotovoltaicItemImportParams photovoltaicItemImportParams) {
        List<PhotovoltaicItem> items = new ArrayList<>();
        XSSFSheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() < photovoltaicItemImportParams.getColumnsToIgnore()) {
                continue;
            }
            if (row.getCell(0) == null || row.getCell(0).getCellType() == CellType.BLANK) {
                break;
            }
            PhotovoltaicItem item = new PhotovoltaicItem();
            item.setInverter(
                    getStringValueFromCell(
                            row.getCell(photovoltaicItemImportParams.getInverterIndex())));
            item.setInverterPower(
                    getBigDecimalValueFromCell(
                            row.getCell(photovoltaicItemImportParams.getInverterPowerIndex())));
            item.setPanelsPower(
                    getBigDecimalValueFromCell(
                            row.getCell(photovoltaicItemImportParams.getPanelsPowerIndex())));
            item.setPanelsQuantity(
                    (int) row.getCell(photovoltaicItemImportParams.getPanelsQuantityIndex()).getNumericCellValue());
            item.setPvPower(
                    getBigDecimalValueFromCell(
                            row.getCell(photovoltaicItemImportParams.getPvPowerIndex())));
            item.setCorePriceCeramicTileSlantRoof(
                    getBigDecimalValueFromCell(
                            row.getCell(photovoltaicItemImportParams.getCorePriceCeramicTileSlantRoofIndex())));
            item.setCorePriceSteelTileSlantRoof(
                    getBigDecimalValueFromCell(
                            row.getCell(photovoltaicItemImportParams.getCorePriceSteelTileSlantRoofIndex())));
            item.setCorePriceSteelSlantRoof(
                    getBigDecimalValueFromCell(
                            row.getCell(photovoltaicItemImportParams.getCorePriceSteelSlantRoofIndex())));
            item.setCorePriceBallastFlatRoof(
                    getBigDecimalValueFromCell(
                            row.getCell(photovoltaicItemImportParams.getCorePriceBallastFlatRoofIndex())));
            item.setCorePriceInvasiveFlatRoof(
                    getBigDecimalValueFromCell(
                            row.getCell(photovoltaicItemImportParams.getCorePriceInvasiveFlatRoofIndex())));
            item.setCorePriceGround(
                    getBigDecimalValueFromCell(
                            row.getCell(photovoltaicItemImportParams.getCorePriceGroundIndex())));
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
