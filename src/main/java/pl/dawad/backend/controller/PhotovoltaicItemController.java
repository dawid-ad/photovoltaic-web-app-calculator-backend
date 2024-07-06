package pl.dawad.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dawad.backend.model.PhotovoltaicItem;
import pl.dawad.backend.service.PhotovoltaicItemService;

import java.util.List;

@RestController
@RequestMapping("/api/photovoltaic-items")
public class PhotovoltaicItemController {

    private final PhotovoltaicItemService photovoltaicItemService;

    public PhotovoltaicItemController(PhotovoltaicItemService photovoltaicItemService) {
        this.photovoltaicItemService = photovoltaicItemService;
    }
    @GetMapping
    public List<PhotovoltaicItem> getAllPhotovoltaicItems() {
        return photovoltaicItemService.getAllPhotovoltaicItems();
    }
    @PostMapping("/add")
    public ResponseEntity<PhotovoltaicItem> addPhotovoltaicItem(@RequestBody PhotovoltaicItem photovoltaicItem) {
        PhotovoltaicItem createdItem = photovoltaicItemService.addPhotovoltaicItem(photovoltaicItem);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }
}
