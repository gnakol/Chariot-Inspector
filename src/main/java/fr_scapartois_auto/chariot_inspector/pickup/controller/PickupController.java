package fr_scapartois_auto.chariot_inspector.pickup.controller;

import fr_scapartois_auto.chariot_inspector.pickup.dto.PickupDTO;
import fr_scapartois_auto.chariot_inspector.pickup.service.PickupService;
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
@RequestMapping("pickup")
public class PickupController {

    private final PickupService pickupService;

    @GetMapping("all-pickup")
    public ResponseEntity<Page<PickupDTO>> allPickup(Pageable pageable)
    {
        return ResponseEntity.ok(this.pickupService.all(pageable));
    }

    @PostMapping("add-new-pickup")
    public ResponseEntity<PickupDTO> addNewPickup(@Validated @RequestBody PickupDTO pickupDTO)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.pickupService.add(pickupDTO));
    }

    @PutMapping("update-new-pickup/{idPickup}")
    public ResponseEntity<PickupDTO> updatePickup(@Validated @PathVariable Long idPickup, @RequestBody PickupDTO pickupDTO)
    {
        return ResponseEntity.status(202).body(this.pickupService.update(idPickup, pickupDTO));
    }

    @GetMapping("get-pickup-by-id/{idPickup}")
    public ResponseEntity<PickupDTO> getPickupById(@Validated @PathVariable Long idPickup)
    {
        return this.pickupService.getById(idPickup)
                .map(pickupDTO -> {
                    log.info("pickup with id : ", +idPickup+ " was found");
                    return new  ResponseEntity<>(pickupDTO, HttpStatus.OK);
                })
                .orElseThrow(() -> {
                    log.error("pickup with id : " +idPickup+ " was not found");
                    return new RuntimeException("sorry this id pickup is not found");
                });
    }
}
