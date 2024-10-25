package pl.dawad.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dawad.backend.model.entity.CalculationDataSettings;

public interface CalculationDataSettingsRepository extends JpaRepository<CalculationDataSettings,Long> {
}
