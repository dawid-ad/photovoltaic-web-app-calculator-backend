package pl.dawad.backend.service;

import org.springframework.stereotype.Service;
import pl.dawad.backend.exception.ResourceNotFoundException;
import pl.dawad.backend.model.PhotovoltaicItem;
import pl.dawad.backend.repository.PhotovoltaicItemRepository;

import java.util.List;

@Service
public class PhotovoltaicItemService {
    private final PhotovoltaicItemRepository photovoltaicItemRepository;
    public PhotovoltaicItemService(PhotovoltaicItemRepository photovoltaicItemRepository) {
        this.photovoltaicItemRepository = photovoltaicItemRepository;
    }
    public PhotovoltaicItem addPhotovoltaicItem(PhotovoltaicItem photovoltaicItem) {
        if (photovoltaicItem == null) {
            throw new IllegalArgumentException("Photovoltaic item can not be null");
        }
        try {
            return photovoltaicItemRepository.save(photovoltaicItem);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save photovoltaic item");
        }
    }
    public PhotovoltaicItem getPhotovoltaicItem(Long id) {
        return photovoltaicItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PhotovoltaicItem not found with id " + id));
    }
    public List<PhotovoltaicItem> getAllPhotovoltaicItems() {
        return photovoltaicItemRepository.findAll();
    }


}
