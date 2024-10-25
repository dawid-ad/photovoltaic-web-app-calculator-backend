package pl.dawad.backend.service.database;

import org.springframework.stereotype.Service;
import pl.dawad.backend.model.entity.CalculationResult;
import pl.dawad.backend.repository.CalculationResultRepository;

import java.util.Optional;

@Service
public class CalculationResultService {

    private final CalculationResultRepository calculationResultRepository;

    public CalculationResultService(CalculationResultRepository calculationResultRepository) {
        this.calculationResultRepository = calculationResultRepository;
    }

    public CalculationResult saveCalculationResult(CalculationResult calculationResult) {
        return calculationResultRepository.save(calculationResult);
    }

    public Optional<CalculationResult> getCalculationResultById(Long id) {
        return calculationResultRepository.findById(id);
    }

    public void deleteCalculationResult(Long id) {
        calculationResultRepository.deleteById(id);
    }
}

