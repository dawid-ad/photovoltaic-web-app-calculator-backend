package pl.dawad.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String inverter;
    private int panelsQuantity;
    private BigDecimal panelsPower;
    private BigDecimal inverterPower;
    private BigDecimal pvPower;
    private BigDecimal corePriceBallastFlatRoof;
    private BigDecimal corePriceInvasiveFlatRoof;
    private BigDecimal corePriceCeramicTileSlantRoof;
    private BigDecimal corePriceSteelTileSlantRoof;
    private BigDecimal corePriceSteelSlantRoof;
    private BigDecimal corePriceGround;
}
