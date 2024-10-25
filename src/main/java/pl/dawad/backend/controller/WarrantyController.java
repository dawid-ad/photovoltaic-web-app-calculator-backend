package pl.dawad.backend.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.dawad.backend.model.entity.Warranty;
import pl.dawad.backend.service.database.WarrantyService;

@RestController
@RequestMapping("/api/warranty")
@Validated
public class WarrantyController {
    private final WarrantyService warrantyService;

    public WarrantyController(WarrantyService warrantyService) {
        this.warrantyService = warrantyService;
    }

    @GetMapping("")
    public Warranty getWarranty() {
        return warrantyService.getWarrantyData();
    }


    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_API_KEY')")
    public ResponseEntity<?> updateWarranty(@Valid @RequestBody Warranty warranty) {
        try {
            Warranty updatedWarranty = warrantyService.updateWarrantyData(warranty);
            return new ResponseEntity<>(updatedWarranty, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while updating the warranty: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
