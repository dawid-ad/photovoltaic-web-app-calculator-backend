package pl.dawad.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dawad.backend.model.entity.EnergyStorage;

public interface EnergyStorageRepository extends JpaRepository<EnergyStorage,Long> {
}
