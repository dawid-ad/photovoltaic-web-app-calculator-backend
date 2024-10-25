package pl.dawad.backend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Warranty {
    @Id
    private Long id = 1L;
    @NotBlank(message = "Panel efficiency is required")
    private String panelEfficiency;

    @NotBlank(message = "Panel product is required")
    private String panelProduct;

    @NotBlank(message = "Inverter is required")
    private String inverter;

    @NotBlank(message = "Construction is required")
    private String construction;

    @NotBlank(message = "Mounting is required")
    private String mounting;

    @Override
    public String toString() {
        return "Warranty{" +
                "id=" + id +
                ", panelEfficiency='" + panelEfficiency + '\'' +
                ", panelProduct='" + panelProduct + '\'' +
                ", inverter='" + inverter + '\'' +
                ", construction='" + construction + '\'' +
                ", mounting='" + mounting + '\'' +
                '}';
    }
}
