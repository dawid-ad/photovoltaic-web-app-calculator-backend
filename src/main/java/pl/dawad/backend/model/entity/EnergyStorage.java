package pl.dawad.backend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class EnergyStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Model is required")
    private String model;

    @NotBlank(message = "Power is required")
    private String power;

    @NotNull(message = "Core price is required")
    @Positive(message = "Core price must be greater than zero")
    private BigDecimal corePrice;

    @Override
    public String toString() {
        return "EnergyStorage{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", power='" + power + '\'' +
                ", corePrice=" + corePrice +
                '}';
    }
}
