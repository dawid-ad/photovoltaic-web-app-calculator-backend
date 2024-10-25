package pl.dawad.backend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class CalculationDataSettings {
    @Id
    private Long id = 1L;

    @NotNull(message = "Gross margin is required")
    @Positive(message = "Gross margin must be greater than zero")
    private BigDecimal grossMargin;

    @NotNull(message = "Expected PV Power is required")
    @Positive(message = "Market Energy price be greater than zero")
    private BigDecimal marketEnergyPricePerKwh;

    @NotNull(message = "Is Grant Available info is required")
    private boolean grantAvailable;

    @NotNull(message = "Power Optimizer Core Price is required")
    private BigDecimal powerOptimizerCorePrice;

    private BigDecimal maxPvPowerForGrant;
    private BigDecimal grantValue;

    @AssertTrue(message = "When grant is available, maxPvPowerForGrant and grantValue must be provided and positive")
    public boolean isGrantFieldsValid() {
        if (!grantAvailable) {
            return true;
        }
        return maxPvPowerForGrant != null && maxPvPowerForGrant.compareTo(BigDecimal.ZERO) > 0
                && grantValue != null && grantValue.compareTo(BigDecimal.ZERO) > 0;
    }

    @Override
    public String toString() {
        return "CalculationDataSettings{" +
                "id=" + id +
                ", grossMargin=" + grossMargin +
                ", marketEnergyPricePerKwh=" + marketEnergyPricePerKwh +
                ", isGrantAvailable=" + grantAvailable +
                ", powerOptimizerCorePrice=" + powerOptimizerCorePrice +
                ", maxPvPowerForGrant=" + maxPvPowerForGrant +
                ", grantValue=" + grantValue +
                '}';
    }
}
