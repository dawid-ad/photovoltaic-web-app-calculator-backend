package pl.dawad.backend.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.dawad.backend.model.entity.CalculationFormData;
import pl.dawad.backend.model.entity.CalculationResult;
import pl.dawad.backend.service.CalculatorService;

@RestController
@RequestMapping("/api/calculate")
@Validated
public class CalculatorController {
    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }
    @PostMapping("")
    public ResponseEntity<CalculationResult> processCalculation(@Valid @RequestBody CalculationFormData calculationFormData) {
        CalculationResult preprocessedContactForm = calculatorService.processCalculation(calculationFormData);
        return ResponseEntity.ok(preprocessedContactForm);
    }
}
