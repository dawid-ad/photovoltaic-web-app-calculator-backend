package pl.dawad.backend.model;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PhotovoltaicItemImportParams {
    private int columnsToIgnore;
    private int inverterIndex;
    private int panelsQuantityIndex;
    private int panelsPowerIndex;
    private int inverterPowerIndex;
    private int pvPowerIndex;
    private int corePriceBallastFlatRoofIndex;
    private int corePriceInvasiveFlatRoofIndex;
    private int corePriceCeramicTileSlantRoofIndex;
    private int corePriceSteelTileSlantRoofIndex;
    private int corePriceSteelSlantRoofIndex;
    private int corePriceGroundIndex;
}
