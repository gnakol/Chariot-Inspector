package fr_scapartois_auto.chariot_inspector.manufacturer.controller;

import fr_scapartois_auto.chariot_inspector.manufacturer.dto.ManufacturerDTO;
import fr_scapartois_auto.chariot_inspector.manufacturer.service.ManufacturerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("manufacturer")
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    @GetMapping("all-manufacturer")
    public ResponseEntity<Page<ManufacturerDTO>> allManufacturer(Pageable pageable)
    {
        return ResponseEntity.ok(this.manufacturerService.all(pageable));
    }

    @PostMapping("add-new-manufacturer")
    public ResponseEntity<ManufacturerDTO> addNewManufacturer(@Validated @RequestBody ManufacturerDTO manufacturerDTO)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.manufacturerService.add(manufacturerDTO));
    }

    @PutMapping("update-manufacturer/{idManufacturer}")
    public ResponseEntity<ManufacturerDTO> updateManufacturer(@Validated @PathVariable Long idManufacturer, @RequestBody ManufacturerDTO manufacturerDTO)
    {
        return ResponseEntity.status(202).body(this.manufacturerService.update(idManufacturer, manufacturerDTO));
    }

    @DeleteMapping("remove-manufacturer/{idManufacturer}")
    public ResponseEntity<String> removeManufacturer(@Validated @PathVariable Long idManufacturer)
    {
        this.manufacturerService.remove(idManufacturer);

        return ResponseEntity.status(202).body("Manufacturer was successfully remove");
    }

    @GetMapping("get-by-id-manufacturer/{idManufacturer}")
    public ResponseEntity<ManufacturerDTO> getByIdManufacturer(@Validated @PathVariable Long idManufacturer)
    {
        return this.manufacturerService.getById(idManufacturer)
                .map(manufacturerDTO -> {
                    log.info("Manufacturer with id : " +idManufacturer+ " was found");
                    return new ResponseEntity<>(manufacturerDTO, HttpStatus.OK);
                })
                .orElseThrow(() -> {
                    log.error("Manufacturer with id : " +idManufacturer+ " was not found");
                    throw new RuntimeException("Sorry this id was lost ");
                });
    }
}
