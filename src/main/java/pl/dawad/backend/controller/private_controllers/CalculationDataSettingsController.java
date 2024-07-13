package pl.dawad.backend.controller.private_controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.dawad.backend.model.CalculationDataSettings;
import pl.dawad.backend.service.CalculationDataSettingsService;

@RestController
@RequestMapping("/api/calculation-settings")
public class CalculationDataSettingsController {
    private final CalculationDataSettingsService calculationDataSettingsService;

    public CalculationDataSettingsController(CalculationDataSettingsService calculationDataSettingsService) {
        this.calculationDataSettingsService = calculationDataSettingsService;
    }
    @GetMapping()
    public ResponseEntity<CalculationDataSettings> getGrossMarginSettings() {
        CalculationDataSettings settings = calculationDataSettingsService.getSettings();
        return ResponseEntity.ok(settings);
    }
    @Transactional
    @PostMapping
    public ResponseEntity<CalculationDataSettings> updateGrossMarginSettings(@RequestBody CalculationDataSettings calculationDataSettings){
        calculationDataSettingsService.updateSettings(calculationDataSettings);
        return ResponseEntity.ok(calculationDataSettings);
    }
}
