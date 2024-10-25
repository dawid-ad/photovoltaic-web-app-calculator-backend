package pl.dawad.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dawad.backend.model.entity.CalculationFormData;

public interface CalculationFormDataRepository extends JpaRepository<CalculationFormData, Long> {
}
