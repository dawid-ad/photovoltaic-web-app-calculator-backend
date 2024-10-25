package pl.dawad.backend.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.dawad.backend.model.entity.CalculationDataSettings;
import pl.dawad.backend.service.database.CalculationDataSettingsService;

@RestController
@RequestMapping("/api/calculation-settings")
@Validated
@PreAuthorize("hasAuthority('ROLE_API_KEY')")
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
    @PostMapping("/update")
    public ResponseEntity<CalculationDataSettings> updateGrossMarginSettings(@Valid @RequestBody CalculationDataSettings calculationDataSettings){
        calculationDataSettingsService.updateSettings(calculationDataSettings);
        return ResponseEntity.ok(calculationDataSettings);
    }
}
