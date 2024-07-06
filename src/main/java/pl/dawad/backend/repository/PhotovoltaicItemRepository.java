package pl.dawad.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dawad.backend.model.PhotovoltaicItem;

public interface PhotovoltaicItemRepository extends JpaRepository<PhotovoltaicItem, Long> {
}
