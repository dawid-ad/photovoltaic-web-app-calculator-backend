package pl.dawad.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dawad.backend.model.entity.Warranty;

public interface WarrantyRepository extends JpaRepository<Warranty, Long> {
}
