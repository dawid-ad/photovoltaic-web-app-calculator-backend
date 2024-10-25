package pl.dawad.backend.service.database;

import org.springframework.stereotype.Service;
import pl.dawad.backend.model.entity.CalculationFormData;
import pl.dawad.backend.repository.CalculationFormDataRepository;

import java.util.Optional;

@Service
public class CalculationFormDataService {

    private final CalculationFormDataRepository calculationFormDataRepository;

    public CalculationFormDataService(CalculationFormDataRepository calculationFormDataRepository) {
        this.calculationFormDataRepository = calculationFormDataRepository;
    }

    public CalculationFormData saveCalculationFormData(CalculationFormData calculationFormData) {
        return calculationFormDataRepository.save(calculationFormData);
    }

    public Optional<CalculationFormData> getCalculationFormDataById(Long id) {
        return calculationFormDataRepository.findById(id);
    }

    public void deleteCalculationFormData(Long id) {
        calculationFormDataRepository.deleteById(id);
    }
}
