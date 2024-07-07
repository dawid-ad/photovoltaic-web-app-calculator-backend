package pl.dawad.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.dawad.backend.exception.ResourceNotFoundException;
import pl.dawad.backend.model.PhotovoltaicItem;
import pl.dawad.backend.service.PhotovoltaicItemUpdateService;
import pl.dawad.backend.service.PhotovoltaicItemService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/photovoltaic-items")
public class PhotovoltaicItemUpdateController {

    private final PhotovoltaicItemUpdateService photovoltaicItemUpdateService;
    private final PhotovoltaicItemService photovoltaicItemService;

    @Autowired
    public PhotovoltaicItemUpdateController(PhotovoltaicItemUpdateService photovoltaicItemUpdateService, PhotovoltaicItemService photovoltaicItemService) {
        this.photovoltaicItemUpdateService = photovoltaicItemUpdateService;
        this.photovoltaicItemService = photovoltaicItemService;
    }

    @GetMapping
    public List<PhotovoltaicItem> getAllPhotovoltaicItems() {
        return photovoltaicItemService.getAllPhotovoltaicItems();
    }

    @GetMapping("/find")
    public ResponseEntity<PhotovoltaicItem> getClosestPhotovoltaicItem(@RequestParam("pvPower") double pvPower){
        try {
            PhotovoltaicItem closestItem = photovoltaicItemService.getClosestPhotovoltaicItemByPower(BigDecimal.valueOf(pvPower));
            return ResponseEntity.ok(closestItem);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Transactional
    @PostMapping("/update")
    public ResponseEntity<String> importPhotovoltaicItems(@RequestParam("file") MultipartFile file,
                                                          @RequestParam("columnsToIgnore") int columnsToIgnore,
                                                          @RequestParam("inverterIndex") int inverterIndex,
                                                          @RequestParam("panelsQuantityIndex") int panelsQuantityIndex,
                                                          @RequestParam("panelsPowerIndex") int panelsPowerIndex,
                                                          @RequestParam("inverterPowerIndex") int inverterPowerIndex,
                                                          @RequestParam("pvPowerIndex") int pvPowerIndex,
                                                          @RequestParam("corePriceBallastFlatRoofIndex") int corePriceBallastFlatRoofIndex,
                                                          @RequestParam("corePriceInvasiveFlatRoofIndex") int corePriceInvasiveFlatRoofIndex,
                                                          @RequestParam("corePriceCeramicTileSlantRoofIndex") int corePriceCeramicTileSlantRoofIndex,
                                                          @RequestParam("corePriceSteelTileSlantRoofIndex") int corePriceSteelTileSlantRoofIndex,
                                                          @RequestParam("corePriceSteelSlantRoofIndex") int corePriceSteelSlantRoofIndex,
                                                          @RequestParam("corePriceGroundIndex") int corePriceGroundIndex) {
        List<PhotovoltaicItem> previousItems = photovoltaicItemService.getAllPhotovoltaicItems();
        try {
            photovoltaicItemUpdateService.importFromExcelFile(file,columnsToIgnore,inverterIndex,
                    panelsQuantityIndex, panelsPowerIndex, inverterPowerIndex,
                    pvPowerIndex, corePriceBallastFlatRoofIndex, corePriceInvasiveFlatRoofIndex,
                    corePriceCeramicTileSlantRoofIndex, corePriceSteelTileSlantRoofIndex, corePriceSteelSlantRoofIndex,
                    corePriceGroundIndex);
            photovoltaicItemService.deleteAll(previousItems);
            return new ResponseEntity<>("Imported successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            photovoltaicItemUpdateService.savePhotovoltaicItems(previousItems);
            return new ResponseEntity<>("Failed to import: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
