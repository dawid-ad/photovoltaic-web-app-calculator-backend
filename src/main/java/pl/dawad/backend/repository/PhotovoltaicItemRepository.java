package pl.dawad.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.dawad.backend.model.PhotovoltaicItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PhotovoltaicItemRepository extends JpaRepository<PhotovoltaicItem, Long> {
    Optional<PhotovoltaicItem> findByPvPower(@Param("pvPower") BigDecimal pvPower);
    @Query("SELECT item.pvPower FROM PhotovoltaicItem item")
    List<BigDecimal> findAllPvPowerValues();
}
