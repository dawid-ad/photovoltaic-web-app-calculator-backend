package pl.dawad.backend.service;

import org.springframework.stereotype.Service;
import pl.dawad.backend.exception.ResourceNotFoundException;
import pl.dawad.backend.model.PhotovoltaicItem;
import pl.dawad.backend.repository.PhotovoltaicItemRepository;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class PhotovoltaicItemService {
    private static final String NO_VALUES_ERROR_MESSAGE = "No PvPower values found. Make sure photovoltaicItems table are not empty.";
    private final PhotovoltaicItemRepository photovoltaicItemRepository;

    public PhotovoltaicItemService(PhotovoltaicItemRepository photovoltaicItemRepository) {
        this.photovoltaicItemRepository = photovoltaicItemRepository;
    }

    public PhotovoltaicItem getClosestPhotovoltaicItemByPower(BigDecimal pvPower) {
        return photovoltaicItemRepository.findByPvPower(findClosestPower(pvPower))
                .orElseThrow(() -> new ResourceNotFoundException(NO_VALUES_ERROR_MESSAGE));
    }

    public BigDecimal findClosestPower(BigDecimal expectedPvPower) {
        List<BigDecimal> allAvailablePowerValues = photovoltaicItemRepository.findAllPvPowerValues();

        if (allAvailablePowerValues.isEmpty()) {
            throw new ResourceNotFoundException(NO_VALUES_ERROR_MESSAGE);
        }

        BigDecimal minPvPower = allAvailablePowerValues.stream()
                .min(Comparator.naturalOrder())
                .orElseThrow(() -> new ResourceNotFoundException(NO_VALUES_ERROR_MESSAGE));

        // Find the closest power less than or equal to expectedPvPower
        Optional<BigDecimal> closestPower = allAvailablePowerValues.stream()
                .filter(pvPower -> pvPower.compareTo(expectedPvPower) <= 0)
                .max(Comparator.naturalOrder());

        return closestPower.orElse(minPvPower); // Return the minimum value if no match found
    }

    public List<PhotovoltaicItem> getAllPhotovoltaicItems() {
        return photovoltaicItemRepository.findAll();
    }

    public void deleteAll(List<PhotovoltaicItem> photovoltaicItems) {
        photovoltaicItemRepository.deleteAll(photovoltaicItems);
    }

}
