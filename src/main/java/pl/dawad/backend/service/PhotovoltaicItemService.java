package pl.dawad.backend.service;

import org.springframework.stereotype.Service;
import pl.dawad.backend.exception.ResourceNotFoundException;
import pl.dawad.backend.model.PhotovoltaicItem;
import pl.dawad.backend.repository.PhotovoltaicItemRepository;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Service
public class PhotovoltaicItemService {
    private final PhotovoltaicItemRepository photovoltaicItemRepository;
    public PhotovoltaicItemService(PhotovoltaicItemRepository photovoltaicItemRepository) {
        this.photovoltaicItemRepository = photovoltaicItemRepository;
    }
    public PhotovoltaicItem getClosestPhotovoltaicItemByPower(BigDecimal pvPower) {
        return photovoltaicItemRepository.findByPvPower(findClosestPower(pvPower))
                .orElseThrow(() -> new ResourceNotFoundException("No PhotovoltaicItems found"));
    }
    public BigDecimal findClosestPower(BigDecimal targetPvPower) {
        List<BigDecimal> allAvailable = photovoltaicItemRepository.findAllPvPowerValues();
        if (allAvailable.isEmpty()) {
            throw new ResourceNotFoundException("No PvPower values found");
        }
        BigDecimal maxPvPower = allAvailable.stream()
                .max(Comparator.naturalOrder())
                .orElseThrow(() -> new ResourceNotFoundException("No PvPower values found"));

        if (targetPvPower.compareTo(maxPvPower) >= 0) {
            return maxPvPower;
        }

        return allAvailable.stream()
                .filter(pvPower -> pvPower.compareTo(targetPvPower) >= 0)
                .min(Comparator.naturalOrder())
                .orElse(maxPvPower);
    }
    public List<PhotovoltaicItem> getAllPhotovoltaicItems() {
        return photovoltaicItemRepository.findAll();
    }

    public void deleteAll(List<PhotovoltaicItem> photovoltaicItems){
        photovoltaicItemRepository.deleteAll(photovoltaicItems);
    }

}
