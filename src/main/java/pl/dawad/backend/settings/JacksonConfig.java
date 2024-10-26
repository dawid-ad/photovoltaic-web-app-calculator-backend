package pl.dawad.backend.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.CoercionAction;
import com.fasterxml.jackson.databind.cfg.CoercionInputShape;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.dawad.backend.enums.*;

/**
 * Jackson configuration for serializing and deserializing JSON objects.
 * This class enables coercion of empty strings ("") that represent enum values
 * to null, facilitating data handling and avoiding errors during deserialization.
 */
@Configuration
public class JacksonConfig {
    /**
     * Creates and configures an ObjectMapper for use in the application.
     * This ObjectMapper is configured to handle Java time types and
     * allows coercion of empty strings for enums to null.
     *
     * @return a configured ObjectMapper with coercion settings for enums.
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // Register the JavaTimeModule to handle Java time types (e.g., LocalDateTime)
        mapper.registerModule(new JavaTimeModule());
        // Configure ObjectMapper to serialize LocalDateTime in ISO 8601 format
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // Add coercion configuration for all relevant enum types
        addCoercionConfig(mapper, CustomerType.class);
        addCoercionConfig(mapper, InstallationType.class);
        addCoercionConfig(mapper, MountTypeForView.class);
        addCoercionConfig(mapper, PowerOptimizersType.class);
        addCoercionConfig(mapper, RoofSurface.class);
        addCoercionConfig(mapper, RoofType.class);
        return mapper;
    }

    /**
     * Adds coercion configuration for the specified enum type.
     * Empty strings ("") that are sent in JSON will be converted to null.
     *
     * @param mapper the ObjectMapper to which the coercion configuration will be added.
     * @param enumClass the enum class for which coercion configuration is added.
     * @param <T> the enum type
     */
    private <T extends Enum<T>> void addCoercionConfig(ObjectMapper mapper, Class<T> enumClass) {
        mapper.coercionConfigFor(enumClass)
                .setCoercion(CoercionInputShape.EmptyString, CoercionAction.AsNull);
    }
}
