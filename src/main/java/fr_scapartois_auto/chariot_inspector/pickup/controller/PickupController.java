package fr_scapartois_auto.chariot_inspector.pickup.controller;

import fr_scapartois_auto.chariot_inspector.cart.dtos.CartDTO;
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

import java.util.List;
import java.util.Map;

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

    @DeleteMapping("remove-pickup-by-id/{idPickup}")
    public ResponseEntity<String> removePickup(@Validated @PathVariable Long idPickup)
    {
        this.pickupService.remove(idPickup);

        return ResponseEntity.status(202).body("Pickup was successfully remove");
    }

    @DeleteMapping("remove-pickup-by-range-id/{startId}/{endId}")
    public ResponseEntity<String> removePickupByIdRange(@Validated @PathVariable Long startId, @PathVariable Long endId)
    {
        this.pickupService.removePickupIdRange(startId, endId);

        return ResponseEntity.status(202).body("Remove by range id pickup was successfully");
    }

    @DeleteMapping("remove-pickup-by-choose-id")
    public ResponseEntity<String> removePickupByChooseId(@Validated @RequestBody List<Long> listIdsPickup)
    {
        this.pickupService.removePickupByChooseId(listIdsPickup);

        return ResponseEntity.status(202).body("Remove pickup by choose id was successfully");
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

    @GetMapping("all-cart-by-account/{idAccount}")
    public ResponseEntity<Page<CartDTO>> allCartByAccount(@Validated @PathVariable Long idAccount, Pageable pageable)
    {
        return ResponseEntity.ok(this.pickupService.allCartByAccount(idAccount, pageable));
    }

    @GetMapping("get-pickup-by-account-id/{idAccount}")
    public ResponseEntity<Page<PickupDTO>> getPickupByAccountId(@Validated @PathVariable Long idAccount, Pageable pageable)
    {
        return ResponseEntity.ok(this.pickupService.getPickupByAccountId(idAccount, pageable));
    }

    @GetMapping("get-id-pickup-by-cart-number")
    public ResponseEntity<List<Long>> getIdPickupByCartNumber(@RequestParam String cartNumber) {
        List<Long> idPickup = pickupService.getIdPickupByCartNumber(cartNumber);
        return ResponseEntity.ok(idPickup);
    }

    @GetMapping("take-pickup-by-work-session-id")
    public ResponseEntity<PickupDTO> getPickupByWorkSessionId(@Validated @RequestParam String workSessionId) {

        return this.pickupService.takePickupByWorkSessionId(workSessionId)
                .map(pickupDTO -> {
                    log.info("Pickup with work session id : " +workSessionId+ " was found");
                    return new ResponseEntity<>(pickupDTO, HttpStatus.OK);
                })
                .orElseThrow(() -> {
                    log.error("Pickup with work session id :" +workSessionId+ " was not found");
                    throw new RuntimeException(" Sorry this pickup by workSessionId was loose");
                });
    }

    @GetMapping("relevant-fields/{cartId}")
    public ResponseEntity<Map<String, Boolean>> getRelevantFields(@Validated @PathVariable Long cartId) {
        return ResponseEntity.ok(this.pickupService.getRelevantFields(cartId));
    }
}
