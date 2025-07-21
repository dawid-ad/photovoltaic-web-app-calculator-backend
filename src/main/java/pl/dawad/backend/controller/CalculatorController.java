package pl.dawad.backend.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.dawad.backend.logger.CalculationLogger;
import pl.dawad.backend.model.entity.CalculationFormData;
import pl.dawad.backend.model.entity.CalculationResult;
import pl.dawad.backend.service.CalculatorService;

@RestController
@RequestMapping("/api/calculate")
@Validated
public class CalculatorController {
    private final CalculatorService calculatorService;
    private final CalculationLogger calculationLogger;

    public CalculatorController(CalculatorService calculatorService, CalculationLogger calculationLogger) {
        this.calculatorService = calculatorService;
        this.calculationLogger = calculationLogger;
    }
    @PostMapping("")
    public ResponseEntity<CalculationResult> processCalculation(@Valid @RequestBody CalculationFormData calculationFormData) {
        CalculationResult preprocessedCalculationForm = calculatorService.processCalculation(calculationFormData);
        calculationLogger.logCalculation(calculationFormData, preprocessedCalculationForm);
        return ResponseEntity.ok(preprocessedCalculationForm);
    }
}
