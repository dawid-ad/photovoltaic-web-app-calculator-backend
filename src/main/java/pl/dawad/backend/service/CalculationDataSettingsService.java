package pl.dawad.backend.service;

import org.springframework.stereotype.Service;
import pl.dawad.backend.exception.ResourceNotFoundException;
import pl.dawad.backend.model.CalculationDataSettings;
import pl.dawad.backend.repository.CalculationDataSettingsRepository;

import java.util.Optional;

@Service
public class CalculationDataSettingsService {
    private final CalculationDataSettingsRepository calculationDataSettingsRepository;

    public CalculationDataSettingsService(CalculationDataSettingsRepository calculationDataSettingsRepository) {
        this.calculationDataSettingsRepository = calculationDataSettingsRepository;
    }

    public CalculationDataSettings getSettings() {
        Optional<CalculationDataSettings> settings = calculationDataSettingsRepository.findById(1L);
        if (settings.isEmpty()) {
            throw new ResourceNotFoundException("Calculation Data Settings must be provided.");
        }
        return settings.get();
    }

    public void updateSettings(CalculationDataSettings settings) {
        settings.setId(1L);
        calculationDataSettingsRepository.save(settings);
    }
}
