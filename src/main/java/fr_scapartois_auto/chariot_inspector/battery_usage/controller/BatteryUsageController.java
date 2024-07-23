package fr_scapartois_auto.chariot_inspector.battery_usage.controller;

import fr_scapartois_auto.chariot_inspector.battery_usage.dto.BatteryUsageDTO;
import fr_scapartois_auto.chariot_inspector.battery_usage.service.BatteryUsageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("battery-usage")
public class BatteryUsageController {

    private final BatteryUsageService batteryUsageService;

    @GetMapping("all-battery-usage")
    public ResponseEntity<Page<BatteryUsageDTO>> allBatteryUsage(Pageable pageable)
    {
        return ResponseEntity.ok(this.batteryUsageService.all(pageable));
    }

    @PostMapping("add-new-battery-usage")
    public ResponseEntity<BatteryUsageDTO> addNewBatteryUsage(@Validated @RequestBody BatteryUsageDTO batteryUsageDTO)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.batteryUsageService.add(batteryUsageDTO));
    }

    @DeleteMapping("remove-battery-usage-by-id/{idBatteryUsage}")
    public ResponseEntity<String> removeBatteryUsage(@Validated @PathVariable Long idBatteryUsage)
    {
        this.batteryUsageService.remove(idBatteryUsage);

        return ResponseEntity.status(202).body("Battery usage was successfully remove");
    }

    @GetMapping("/get-battery-usage-by-cart-id/{cartId}")
    public ResponseEntity<List<BatteryUsageDTO>> getBatteryUsageByCartId(@PathVariable Long cartId) {
        List<BatteryUsageDTO> batteryUsages = batteryUsageService.getBatteryUsageByCartId(cartId);
        return ResponseEntity.ok(batteryUsages);
    }
}
