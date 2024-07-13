package pl.dawad.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import pl.dawad.backend.validation.ValidateForCalculation;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class CalculationDataSettings {
    @Id
    private Long id;
    @ValidateForCalculation
    private BigDecimal grossMarginFrom;
    @ValidateForCalculation
    private BigDecimal grossMarginTo;
    @ValidateForCalculation
    private BigDecimal energyPricePerKwh;
}
