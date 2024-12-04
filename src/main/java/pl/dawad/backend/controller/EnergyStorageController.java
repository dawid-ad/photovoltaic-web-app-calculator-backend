package pl.dawad.backend.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.dawad.backend.model.dto.EnergyStorageModelDto;
import pl.dawad.backend.model.entity.EnergyStorage;
import pl.dawad.backend.service.database.EnergyStorageService;

import java.util.List;

@RestController
@RequestMapping("/api/energy-storage")
@Validated
public class EnergyStorageController {
    private final EnergyStorageService energyStorageService;

    public EnergyStorageController(EnergyStorageService energyStorageService) {
        this.energyStorageService = energyStorageService;
    }

    /**
     * Saves multiple EnergyStorage items.
     *
     * @param energyStorageList the list of EnergyStorage items to save
     * @return the saved EnergyStorage items
     */
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_API_KEY')")
    public ResponseEntity<List<EnergyStorage>> updateEnergyStorage(
            @RequestBody @Valid List<EnergyStorage> energyStorageList) {
        List<EnergyStorage> updatedEnergyStorageList = energyStorageService.replaceEnergyStorageList(energyStorageList);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedEnergyStorageList);
    }

    /**
     * Retrieves an EnergyStorage item by its ID.
     *
     * @param id the ID of the EnergyStorage item to retrieve
     * @return the EnergyStorage item
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_API_KEY')")
    public ResponseEntity<EnergyStorage> getEnergyStorageById(@PathVariable Long id) {
        EnergyStorage energyStorage = energyStorageService.getEnergyStorageById(id);
        return ResponseEntity.ok(energyStorage);
    }

    /**
     * Retrieves all EnergyStorage items.
     *
     * @return a list of all EnergyStorage items
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_API_KEY')")
    public ResponseEntity<List<EnergyStorage>> getAllEnergyStorage() {
        List<EnergyStorage> energyStorageList = energyStorageService.getAllEnergyStorage();
        return ResponseEntity.ok(energyStorageList);
    }

    /**
     * Updates an existing EnergyStorage item.
     *
     * @param id            the ID of the EnergyStorage item to update
     * @param energyStorage the updated EnergyStorage item
     * @return the updated EnergyStorage item
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_API_KEY')")
    public ResponseEntity<EnergyStorage> updateEnergyStorage(@PathVariable Long id,
                                                             @RequestBody @Valid EnergyStorage energyStorage) {
        energyStorage.setId(id);
        EnergyStorage updatedEnergyStorage = energyStorageService.saveEnergyStorage(energyStorage);
        return ResponseEntity.ok(updatedEnergyStorage);
    }

    /**
     * Deletes an EnergyStorage item by its ID.
     *
     * @param id the ID of the EnergyStorage item to delete
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_API_KEY')")
    public ResponseEntity<Void> deleteEnergyStorage(@PathVariable Long id) {
        energyStorageService.removeEnergyStorageById(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    /**
     * Deletes all EnergyStorage items.
     *
     * @return a response indicating the deletion was successful
     */
    @DeleteMapping
    @PreAuthorize("hasAuthority('ROLE_API_KEY')")
    public ResponseEntity<Void> deleteAllEnergyStorage() {
        energyStorageService.deleteAllEnergyStorage();
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    /**
     * Retrieves all EnergyStorage models and powers without prices.
     *
     * @return a list of EnergyStorage models and powers
     */
    @GetMapping("/models")
    public ResponseEntity<List<EnergyStorageModelDto>> getAllEnergyStorageModels() {
        List<EnergyStorageModelDto> models = energyStorageService.getAllEnergyStorageModels();
        return ResponseEntity.ok(models);
    }
}
