package pl.dawad.backend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class PhotovoltaicItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Inverter model is required")
    private String inverterModel;

    @NotBlank(message = "Module model is required")
    private String moduleModel;

    @Min(value = 1, message = "Module power must be greater than zero")
    private int modulePower;

    @Min(value = 1, message = "Panels quantity must be greater than zero")
    private int panelsQuantity;

    @NotNull(message = "Inverter power is required")
    @Positive(message = "Inverter power must be greater than zero")
    private BigDecimal inverterPower;

    @NotNull(message = "PV power is required")
    @Positive(message = "PV power must be greater than zero")
    private BigDecimal pvPower;

    @NotNull(message = "Core price for ballast flat roof is required")
    @Positive(message = "Core price for ballast flat roof must be greater than zero")
    private BigDecimal corePriceBallastFlatRoof;

    private BigDecimal corePriceInvasiveFlatRoof;

    @NotNull(message = "Core price for ceramic tile slant roof is required")
    @Positive(message = "Core price for ceramic tile slant roof must be greater than zero")
    private BigDecimal corePriceCeramicTileSlantRoof;

    @NotNull(message = "Core price for steel tile slant roof is required")
    @Positive(message = "Core price for steel tile slant roof must be greater than zero")
    private BigDecimal corePriceSteelTileSlantRoof;

    @NotNull(message = "Core price for steel slant roof is required")
    @Positive(message = "Core price for steel slant roof must be greater than zero")
    private BigDecimal corePriceSteelSlantRoof;

    @NotNull(message = "Core price for ground is required")
    @Positive(message = "Core price for ground must be greater than zero")
    private BigDecimal corePriceGround;

    private BigDecimal corePriceProjoy;

    @NotNull(message = "Core price for fire button is required")
    @Positive(message = "Core price for fire button must be greater than zero")
    private BigDecimal corePriceFireButton;

    private BigDecimal corePriceHybridInverter;

    @NotNull(message = "Is Energy Storage Available is required")
    private boolean isEnergyStorageAvailable;

    @Override
    public String toString() {
        return "PhotovoltaicItem{" +
                "id=" + id +
                ", inverterModel='" + inverterModel + '\'' +
                ", moduleModel='" + moduleModel + '\'' +
                ", modulePower=" + modulePower +
                ", panelsQuantity=" + panelsQuantity +
                ", inverterPower=" + inverterPower +
                ", pvPower=" + pvPower +
                ", corePriceBallastFlatRoof=" + corePriceBallastFlatRoof +
                ", corePriceInvasiveFlatRoof=" + corePriceInvasiveFlatRoof +
                ", corePriceCeramicTileSlantRoof=" + corePriceCeramicTileSlantRoof +
                ", corePriceSteelTileSlantRoof=" + corePriceSteelTileSlantRoof +
                ", corePriceSteelSlantRoof=" + corePriceSteelSlantRoof +
                ", corePriceGround=" + corePriceGround +
                ", corePriceProjoy=" + corePriceProjoy +
                ", corePriceFireButton=" + corePriceFireButton +
                ", corePriceHybridInverter=" + corePriceHybridInverter +
                ", isEnergyStorageAvailable=" + isEnergyStorageAvailable +
                '}';
    }
}

