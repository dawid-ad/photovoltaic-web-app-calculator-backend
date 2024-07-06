package pl.dawad.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PhotovoltaicItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String inverter;
    private int panelsQuantity;
    private double panelsPower;
    private double inverterPower;
    private double pvPower;
    private double corePriceBallastFlatRoof;
    private double corePriceInvasiveFlatRoof;
    private double corePriceCeramicTileSlantRoof;
    private double corePriceSteelTileSlantRoof;
    private double corePriceGround;
}
