package pl.dawad.backend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class CalculationResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime calculationDate;
    private BigDecimal proposedPvPower;
    private BigDecimal estimatedOneYearProduction;
    private String inverterModel;
    private String mountTypeForView;
    private BigDecimal price;
    private BigDecimal priceWithoutGrant;
    private String moduleModel;
    private int modulePower;
    private int panelsQuantity;
    private double vatTax;
    private BigDecimal pricePerKw;
    private BigDecimal energyPricePerKwh;
    private boolean projoyIncluded;
    private boolean grantPossible;
    private boolean energyStorageAvailable;

    @Override
    public String toString() {
        return "CalculationResult{" +
                "id=" + id +
                ", calculationDate=" + calculationDate +
                ", proposedPvPower=" + proposedPvPower +
                ", estimatedOneYearProduction=" + estimatedOneYearProduction +
                ", inverterModel='" + inverterModel + '\'' +
                ", mountTypeForView='" + mountTypeForView + '\'' +
                ", price=" + price +
                ", priceWithoutGrant=" + priceWithoutGrant +
                ", moduleModel='" + moduleModel + '\'' +
                ", modulePower=" + modulePower +
                ", panelsQuantity=" + panelsQuantity +
                ", vatTax=" + vatTax +
                ", pricePerKw=" + pricePerKw +
                ", energyPricePerKwh=" + energyPricePerKwh +
                ", projoyIncluded=" + projoyIncluded +
                ", grantPossible=" + grantPossible +
                ", energyStorageAvailable=" + energyStorageAvailable +
                '}';
    }
}
