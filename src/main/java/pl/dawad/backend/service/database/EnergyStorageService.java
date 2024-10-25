package pl.dawad.backend.service.database;

import org.springframework.stereotype.Service;
import pl.dawad.backend.exception.ResourceNotFoundException;
import pl.dawad.backend.model.dto.EnergyStorageModelDto;
import pl.dawad.backend.model.entity.EnergyStorage;
import pl.dawad.backend.repository.EnergyStorageRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnergyStorageService {
    private final EnergyStorageRepository energyStorageRepository;

    public EnergyStorageService(EnergyStorageRepository energyStorageRepository) {
        this.energyStorageRepository = energyStorageRepository;
    }

    public EnergyStorage saveEnergyStorage(EnergyStorage energyStorage) {
        return energyStorageRepository.save(energyStorage);
    }

    public List<EnergyStorage> saveEnergyStorageList(List<EnergyStorage> energyStorageList) {
        return energyStorageList.stream()
                .map(this::saveEnergyStorage)
                .collect(Collectors.toList());
    }

    public EnergyStorage getEnergyStorageById(Long id) {
        return energyStorageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EnergyStorage not found with id: " + id));
    }

    public void removeEnergyStorageById(Long id) {
        if (!energyStorageRepository.existsById(id)) {
            throw new ResourceNotFoundException("EnergyStorage not found with id: " + id);
        }
        energyStorageRepository.deleteById(id);
    }

    public void deleteAllEnergyStorage() {
        energyStorageRepository.deleteAll();
    }

    public List<EnergyStorage> getAllEnergyStorage() {
        return energyStorageRepository.findAll();
    }
    public List<EnergyStorageModelDto> getAllEnergyStorageModels() {
        return energyStorageRepository.findAll().stream()
                .map(storage -> new EnergyStorageModelDto(storage.getId(), storage.getModel(), storage.getPower()))
                .collect(Collectors.toList());
    }
}
