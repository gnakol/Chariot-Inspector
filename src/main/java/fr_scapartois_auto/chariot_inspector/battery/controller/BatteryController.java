package fr_scapartois_auto.chariot_inspector.battery.controller;

import fr_scapartois_auto.chariot_inspector.battery.dtos.BatteryDTO;
import fr_scapartois_auto.chariot_inspector.battery.services.BatteryService;
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
@RequestMapping("battery")
@Slf4j
public class BatteryController {

    private final BatteryService batteryService;

    @GetMapping("all-battery")
    public ResponseEntity<Page<BatteryDTO>> allBattery(Pageable pageable)
    {
        return ResponseEntity.ok(this.batteryService.all(pageable));
    }

    @PostMapping("add-new-battery")
    public ResponseEntity<BatteryDTO> addNewBattery(@Validated @RequestBody BatteryDTO batteryDTO)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.batteryService.add(batteryDTO));
    }

    @PutMapping("update-battery/{idBattery}")
    public ResponseEntity<BatteryDTO> updateBattery(@Validated @PathVariable Long idBattery, @RequestBody BatteryDTO batteryDTO)
    {
        return ResponseEntity.status(202).body(this.batteryService.update(idBattery, batteryDTO));
    }

    @DeleteMapping("remove-battery/{idBattery}")
    public ResponseEntity<String> removeBattery(@Validated @PathVariable Long idBattery)
    {
        this.batteryService.remove(idBattery);

        return ResponseEntity.status(202).body("Battery with id :" +idBattery+ " was successfully delete");
    }

    @GetMapping("get-battery-by-id/{idBattery}")
    public ResponseEntity<BatteryDTO> getBatteryById(@Validated @PathVariable Long idBattery)
    {
        return this.batteryService.getById(idBattery)
                .map(batteryDTO -> {
                    log.info("Battery with id : " +idBattery+ " was found");
                   return new ResponseEntity<>(batteryDTO, HttpStatus.OK);
                })
                .orElseThrow(() -> {
                    log.error("Battery with id : " +idBattery+ " was not found");
                    throw new RuntimeException("Error find battery");
                });
    }
}
