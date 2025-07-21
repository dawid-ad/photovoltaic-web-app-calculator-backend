package pl.dawad.backend.logger;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import pl.dawad.backend.model.entity.CalculationFormData;
import pl.dawad.backend.model.entity.CalculationResult;

import java.io.*;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CalculationLogger {

    private static final String LOG_FILE_PREFIX = "calculation_logs/calculations-";
    private static final String LOG_FILE_SUFFIX = ".xlsx";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private String lastUserID = null;
    private CalculationFormData lastFormData = null;
    private CalculationResult lastResult = null;
    private boolean isSameUser = false;

    private String getLogFileName() {
        String date = LocalDate.now().format(DATE_FORMATTER);
        return LOG_FILE_PREFIX + date + LOG_FILE_SUFFIX;
    }

    public void logCalculation(CalculationFormData calculationFormData,
                               CalculationResult calculationResult) {
        try {
            String excelFilePath = getLogFileName();
            boolean fileExists = new File(excelFilePath).exists();

            try (
                    FileInputStream fis = fileExists ? new FileInputStream(excelFilePath) : null;
                    XSSFWorkbook workbook = fileExists ? new XSSFWorkbook(fis) : new XSSFWorkbook();
                    FileOutputStream fos = new FileOutputStream(excelFilePath)) {

                XSSFSheet sheet = fileExists ? workbook.getSheetAt(0) : workbook.createSheet("Log");

                if (!fileExists) {
                    Row header = sheet.createRow(0);
                    int i = 0;
                    header.createCell(i++).setCellValue("Godzina");
                    header.createCell(i++).setCellValue("User ID");
                    header.createCell(i++).setCellValue("Słowo kluczowe");
                    header.createCell(i++).setCellValue("Typ Klienta");
                    header.createCell(i++).setCellValue("Region");
                    header.createCell(i++).setCellValue("Typ instalacji");
                    header.createCell(i++).setCellValue("Dach");
                    header.createCell(i++).setCellValue("Co na dachu");
                    header.createCell(i++).setCellValue("Oczekiwana moc PV");
                    header.createCell(i++).setCellValue("Roczne zużycie energii");
                    header.createCell(i++).setCellValue("Projoy?");
                    header.createCell(i++).setCellValue("Przycisk PPOŻ?");
                    header.createCell(i++).setCellValue("Typ optymizatorów");
                    header.createCell(i++).setCellValue("Magazyn energii?");
                    header.createCell(i++).setCellValue("ID modelu magazynu energii");
                    header.createCell(i++).setCellValue("Dotacja?");
                    header.createCell(i++).setCellValue("Proponowana moc PV");
                    header.createCell(i++).setCellValue("Szacowana produkcja roczna");
                    header.createCell(i++).setCellValue("Model inwertera");
                    header.createCell(i++).setCellValue("Model modułu");
                    header.createCell(i++).setCellValue("Moc modułu");
                    header.createCell(i++).setCellValue("Liczba paneli");
                    header.createCell(i++).setCellValue("Typ montażu");
                    header.createCell(i++).setCellValue("Cena z dotacją");
                    header.createCell(i++).setCellValue("Cena bez dotacji");
                    header.createCell(i++).setCellValue("Cena za kW");
                    header.createCell(i++).setCellValue("Rodzaj podatku");
                    header.createCell(i++).setCellValue("Cena energii za kWh");
                    header.createCell(i++).setCellValue("Projoy w cenie");
                    header.createCell(i++).setCellValue("Dotacja możliwa");
                    header.createCell(i++).setCellValue("Magazyn dostępny");
                }

                isSameUser = lastUserID != null && calculationFormData.getUserId() != null && lastUserID.equals(calculationFormData.getUserId());

                Row row = sheet.createRow(sheet.getLastRowNum() + 1);
                int col = 0;

                row.createCell(col++).setCellValue(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                row.createCell(col++).setCellValue(calculationFormData.getUserId());
                row.createCell(col++).setCellValue(logIfChanged(calculationFormData.getUtmTerm(), lastFormData != null ? lastFormData.getUtmTerm() : null));
                row.createCell(col++).setCellValue(logIfChanged(calculationFormData.getCustomerType(), lastFormData != null ? lastFormData.getCustomerType() : null));
                row.createCell(col++).setCellValue(logIfChanged(calculationFormData.getRegion(), lastFormData != null ? lastFormData.getRegion() : null));
                row.createCell(col++).setCellValue(logIfChanged(calculationFormData.getInstallationType(), lastFormData != null ? lastFormData.getInstallationType() : null));
                row.createCell(col++).setCellValue(logIfChanged(calculationFormData.getRoofType(), lastFormData != null ? lastFormData.getRoofType() : null));
                row.createCell(col++).setCellValue(logIfChanged(calculationFormData.getRoofSurface(), lastFormData != null ? lastFormData.getRoofSurface() : null));
                row.createCell(col++).setCellValue(logIfChanged(calculationFormData.getExpectedPvPower(), lastFormData != null ? lastFormData.getExpectedPvPower() : null));
                row.createCell(col++).setCellValue(logIfChanged(calculationFormData.getEnergyConsumptionPerYear(), lastFormData != null ? lastFormData.getEnergyConsumptionPerYear() : null));
                row.createCell(col++).setCellValue(logIfChanged(calculationFormData.isProjoy(), lastFormData != null ? lastFormData.isProjoy() : null));
                row.createCell(col++).setCellValue(logIfChanged(calculationFormData.isFireButton(), lastFormData != null ? lastFormData.isFireButton() : null));
                row.createCell(col++).setCellValue(logIfChanged(calculationFormData.getPowerOptimizersType(), lastFormData != null ? lastFormData.getPowerOptimizersType() : null));
                row.createCell(col++).setCellValue(logIfChanged(calculationFormData.getEnergyStorageModelId() > 0 ? "TAK" : "NIE",
                        lastFormData != null && lastFormData.getEnergyStorageModelId() > 0 ? "TAK" : "NIE"));
                row.createCell(col++).setCellValue(logIfChanged(calculationFormData.getEnergyStorageModelId(), lastFormData != null ? lastFormData.getEnergyStorageModelId() : null));
                row.createCell(col++).setCellValue(logIfChanged(calculationFormData.isHasGrant(), lastFormData != null ? lastFormData.isHasGrant() : null));

                row.createCell(col++).setCellValue(logIfChanged(calculationResult.getProposedPvPower(), lastResult != null ? lastResult.getProposedPvPower() : null));
                row.createCell(col++).setCellValue(logIfChanged(calculationResult.getEstimatedOneYearProduction(), lastResult != null ? lastResult.getEstimatedOneYearProduction() : null));
                row.createCell(col++).setCellValue(logIfChanged(calculationResult.getInverterModel(), lastResult != null ? lastResult.getInverterModel() : null));
                row.createCell(col++).setCellValue(logIfChanged(calculationResult.getModuleModel(), lastResult != null ? lastResult.getModuleModel() : null));
                row.createCell(col++).setCellValue(logIfChanged(calculationResult.getModulePower(), lastResult != null ? lastResult.getModulePower() : null));
                row.createCell(col++).setCellValue(logIfChanged(calculationResult.getPanelsQuantity(), lastResult != null ? lastResult.getPanelsQuantity() : null));
                row.createCell(col++).setCellValue(logIfChanged(calculationResult.getMountTypeForView(), lastResult != null ? lastResult.getMountTypeForView() : null));
                row.createCell(col++).setCellValue(logIfChanged(calculationResult.getPrice().setScale(0, RoundingMode.HALF_UP),
                        lastResult != null ? lastResult.getPrice().setScale(0, RoundingMode.HALF_UP) : null));
                row.createCell(col++).setCellValue(logIfChanged(calculationResult.getPriceWithoutGrant().setScale(0, RoundingMode.HALF_UP),
                        lastResult != null ? lastResult.getPriceWithoutGrant().setScale(0, RoundingMode.HALF_UP) : null));
                row.createCell(col++).setCellValue(logIfChanged(calculationResult.getPricePerKw().setScale(0, RoundingMode.HALF_UP),
                        lastResult != null ? lastResult.getPricePerKw().setScale(0, RoundingMode.HALF_UP) : null));
                row.createCell(col++).setCellValue(logIfChanged(calculationResult.getVatTax(), lastResult != null ? lastResult.getVatTax() : null));
                row.createCell(col++).setCellValue(logIfChanged(calculationResult.getEnergyPricePerKwh(), lastResult != null ? lastResult.getEnergyPricePerKwh() : null));
                row.createCell(col++).setCellValue(logIfChanged(calculationResult.isProjoyIncluded(), lastResult != null ? lastResult.isProjoyIncluded() : null));
                row.createCell(col++).setCellValue(logIfChanged(calculationResult.isGrantPossible(), lastResult != null ? lastResult.isGrantPossible() : null));
                row.createCell(col++).setCellValue(logIfChanged(calculationResult.isEnergyStorageAvailable(), lastResult != null ? lastResult.isEnergyStorageAvailable() : null));

                workbook.write(fos);

                lastUserID = calculationFormData.getUserId();
                lastFormData = calculationFormData;
                lastResult = calculationResult;

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("It seems to be a problem with the log process: " + e.getMessage());
        }

    }

    private String logIfChanged(Object newValue, Object oldValue) {
        if (!isSameUser) return newValue != null ? newValue.toString() : "";
        if (newValue == null && oldValue == null) return "";
        if (newValue == null || oldValue == null || !newValue.equals(oldValue)) {
            return newValue != null ? newValue.toString() : "";
        }
        return "";
    }
}
