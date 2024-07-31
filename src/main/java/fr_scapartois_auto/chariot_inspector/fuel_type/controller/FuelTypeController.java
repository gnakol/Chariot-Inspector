package fr_scapartois_auto.chariot_inspector.fuel_type.controller;

import fr_scapartois_auto.chariot_inspector.fuel_type.dto.FuelTypeDTO;
import fr_scapartois_auto.chariot_inspector.fuel_type.service.FuelTypeService;
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
@RequestMapping("fuel-type")
public class FuelTypeController {

    private final FuelTypeService fuelTypeService;

    @GetMapping("all-fuel-type")
    public ResponseEntity<Page<FuelTypeDTO>> allFuelType(Pageable pageable)
    {
        return ResponseEntity.ok(this.fuelTypeService.all(pageable));
    }

    @PostMapping("add-new-fuel-type")
    public ResponseEntity<FuelTypeDTO> addNewFuelType(@Validated @RequestBody FuelTypeDTO fuelTypeDTO)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.fuelTypeService.add(fuelTypeDTO));
    }

    @PutMapping("update-fuel-type/{idFuelType}")
    public ResponseEntity<FuelTypeDTO> updateFuelType(@Validated @PathVariable Long idFuelType, @RequestBody FuelTypeDTO fuelTypeDTO)
    {
        return ResponseEntity.status(202).body(this.fuelTypeService.update(idFuelType, fuelTypeDTO));
    }

    @DeleteMapping("remove-fuel-type/{idFuelType}")
    public ResponseEntity<String> removeFuelType(@Validated  @PathVariable Long idFuelType)
    {
        this.fuelTypeService.remove(idFuelType);

        return ResponseEntity.status(202).body("Fuel type was successfully remove");
    }

    @GetMapping("get-by-id-fuel-type/{idFuelType}")
    public ResponseEntity<FuelTypeDTO> getFuelTypeById(@Validated @PathVariable Long idFuelType)
    {
        return this.fuelTypeService.getById(idFuelType)
                .map(fuelTypeDTO -> {
                    log.info("Fuel type with id : " +idFuelType+ " was found");
                    return new ResponseEntity<>(fuelTypeDTO, HttpStatus.OK);
                })
                .orElseThrow(() -> {
                    log.error("Fuel type with id : " +idFuelType+ " was not found");
                    throw  new RuntimeException("sorry id fuel type was not found");
                });
    }
}
