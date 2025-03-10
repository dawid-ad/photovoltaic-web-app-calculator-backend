package pl.dawad.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.dawad.backend.model.entity.PhotovoltaicItem;
import pl.dawad.backend.model.PhotovoltaicItemImportParams;
import pl.dawad.backend.service.PhotovoltaicItemUpdateService;
import pl.dawad.backend.service.database.PhotovoltaicItemService;

import java.util.List;

@RestController
@RequestMapping("/api/photovoltaic-items")
@Validated
@PreAuthorize("hasAuthority('ROLE_API_KEY')")
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

    @Transactional
    @PostMapping("/update")
    public ResponseEntity<String> importPhotovoltaicItems(@RequestParam("file") MultipartFile file,
                                                          @ModelAttribute PhotovoltaicItemImportParams photovoltaicItemImportParams) {
        List<PhotovoltaicItem> previousItems = photovoltaicItemService.getAllPhotovoltaicItems();
        try {
            photovoltaicItemUpdateService.importFromExcelFile(file,photovoltaicItemImportParams);
            photovoltaicItemService.deleteAll(previousItems);
            return new ResponseEntity<>("Imported successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            photovoltaicItemUpdateService.savePhotovoltaicItems(previousItems);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
