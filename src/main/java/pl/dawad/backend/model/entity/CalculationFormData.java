package pl.dawad.backend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pl.dawad.backend.enums.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class CalculationFormData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Region is required")
    private String region;

    @NotNull(message = "Customer type is required")
    private CustomerType customerType;

    private RoofType roofType;
    private RoofSurface roofSurface;

    @NotNull(message = "Installation type is required")
    private InstallationType installationType;

    private BigDecimal expectedPvPower;
    private BigDecimal energyConsumptionPerYear;

    private boolean projoy;
    private boolean fireButton;
    private PowerOptimizersType powerOptimizersType;
    private Long energyStorageModelId;
    private boolean hasGrant;

    @AssertTrue(message = "Either Expected PV Power or Energy Consumption Per Year must be provided and greater than zero")
    public boolean isAtLeastOneFieldValid() {
        return (expectedPvPower != null && expectedPvPower.compareTo(BigDecimal.ZERO) > 0) ||
                (energyConsumptionPerYear != null && energyConsumptionPerYear.compareTo(BigDecimal.ZERO) > 0);
    }

    @AssertTrue(message = "Roof type must be provided if installation type is not 'GROUND'")
    public boolean isRoofTypeValid() {
        return installationType == InstallationType.GROUND || roofType != null;
    }

    @AssertTrue(message = "Roof surface must be provided if roof type is 'SLANT_ROOF'")
    public boolean isRoofSurfaceValid() {
        return roofType != RoofType.SLANT_ROOF || (roofSurface != null && !roofSurface.toString().isBlank());
    }

    @Override
    public String toString() {
        return "CalculationFormData{" +
                "id=" + id +
                ", region='" + region + '\'' +
                ", customerType=" + customerType +
                ", roofType=" + roofType +
                ", roofSurface=" + roofSurface +
                ", installationType=" + installationType +
                ", expectedPvPower=" + expectedPvPower +
                ", energyConsumptionPerYear=" + energyConsumptionPerYear +
                ", projoy=" + projoy +
                ", fireButton=" + fireButton +
                ", powerOptimizersType=" + powerOptimizersType +
                ", energyStorageModelId=" + energyStorageModelId +
                ", hasGrant=" + hasGrant +
                '}';
    }
}