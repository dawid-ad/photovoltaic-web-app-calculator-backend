package pl.dawad.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dawad.backend.model.entity.CalculationResult;

public interface CalculationResultRepository extends JpaRepository<CalculationResult, Long> {
}
