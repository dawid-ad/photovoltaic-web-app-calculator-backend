package pl.dawad.backend.controller.public_controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dawad.backend.model.ContactForm;
import pl.dawad.backend.service.CalculatorService;

@RestController
@RequestMapping("/public/")
public class CalculatorController {
    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }
    @GetMapping("/calculate")
    public ResponseEntity<ContactForm> processCalculation(@RequestBody ContactForm contactForm) {
        ContactForm preprocessedContactForm = calculatorService.processCalculation(contactForm);
        return ResponseEntity.ok(preprocessedContactForm);
    }
}
