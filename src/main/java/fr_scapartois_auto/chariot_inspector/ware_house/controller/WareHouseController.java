package fr_scapartois_auto.chariot_inspector.ware_house.controller;

import fr_scapartois_auto.chariot_inspector.ware_house.dto.WareHouseDTO;
import fr_scapartois_auto.chariot_inspector.ware_house.service.WareHouseService;
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
@RequestMapping("ware-house")
public class WareHouseController {

    private final WareHouseService wareHouseService;

    @GetMapping("all-ware-house")
    public ResponseEntity<Page<WareHouseDTO>> allWareHouse(Pageable pageable)
    {
        return ResponseEntity.ok(this.wareHouseService.all(pageable));
    }

    @PostMapping("add-new-ware-house")
    public ResponseEntity<WareHouseDTO> addNewWareHouse(@Validated @RequestBody WareHouseDTO wareHouseDTO)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.wareHouseService.add(wareHouseDTO));
    }

    @PutMapping("update-ware-house/{idWareHouse}")
    public ResponseEntity<WareHouseDTO> updateWareHouse(@Validated @PathVariable Long idWareHouse, @RequestBody WareHouseDTO wareHouseDTO)
    {
        return ResponseEntity.status(202).body(this.wareHouseService.update(idWareHouse, wareHouseDTO));
    }

    @DeleteMapping("remove-ware-house/{idWareHouse}")
    public ResponseEntity<String> removeWareHouse(@Validated @PathVariable Long idWareHouse)
    {
        this.wareHouseService.remove(idWareHouse);

        return ResponseEntity.status(202).body("Ware house was successfully remove");
    }

    @GetMapping("get-by-id-ware-house/{idWareHouse}")
    public ResponseEntity<WareHouseDTO> getByIdWareHouse(@Validated @PathVariable Long idWareHouse)
    {
        return this.wareHouseService.getById(idWareHouse)
                .map(wareHouseDTO -> {
                    log.info("Ware house with id : " +idWareHouse+ " was found");
                    return new ResponseEntity<>(wareHouseDTO, HttpStatus.OK);
                })
                .orElseThrow(() -> {
                    log.error("Ware house with id : " +idWareHouse+ " was not found");
                    throw new RuntimeException("Sorry ware house id was lost");
                });
    }
}
