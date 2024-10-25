package pl.dawad.backend.service.database;

import org.springframework.stereotype.Service;
import pl.dawad.backend.exception.ResourceNotFoundException;
import pl.dawad.backend.model.entity.Warranty;
import pl.dawad.backend.repository.WarrantyRepository;

import java.lang.reflect.Field;
import java.util.Optional;

@Service
public class WarrantyService {
    private final WarrantyRepository warrantyRepository;

    public WarrantyService(WarrantyRepository warrantyRepository) {
        this.warrantyRepository = warrantyRepository;
    }

    public Warranty getWarrantyData() {
        Optional<Warranty> warrantyData = warrantyRepository.findById(1L);
        if (warrantyData.isEmpty()) {
            throw new ResourceNotFoundException("Warranty Data is not provided!");
        }
        return warrantyData.get();
    }

    public Warranty updateWarrantyData(Warranty updatedWarranty) throws IllegalAccessException {
        Optional<Warranty> existingWarrantyOpt = warrantyRepository.findById(1L);

        if (existingWarrantyOpt.isPresent()) {
            Warranty existingWarranty = existingWarrantyOpt.get();

            // Get all fields of Warranty class
            Field[] fields = Warranty.class.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true); // Access private fields
                Object updatedValue = field.get(updatedWarranty);
                if (updatedValue != null) {
                    // Set updated value to the existing warranty object
                    field.set(existingWarranty, updatedValue);
                }
            }
            return warrantyRepository.save(existingWarranty);
        } else {
            return warrantyRepository.save(updatedWarranty);
        }
    }
}
