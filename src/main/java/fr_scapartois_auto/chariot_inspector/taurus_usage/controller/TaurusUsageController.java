package fr_scapartois_auto.chariot_inspector.taurus_usage.controller;

import fr_scapartois_auto.chariot_inspector.accompanied.beans.taurus.dto.TaurusDTO;
import fr_scapartois_auto.chariot_inspector.taurus_usage.dto.TaurusUsageDTO;
import fr_scapartois_auto.chariot_inspector.taurus_usage.service.TaurusUsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("taurus-usage")
public class TaurusUsageController {

    private final TaurusUsageService taurusUsageService;

    @GetMapping("all-taurus-usage")
    public ResponseEntity<Page<TaurusUsageDTO>> allTaurusUsage(Pageable pageable)
    {
        return ResponseEntity.ok(this.taurusUsageService.all(pageable));
    }

    @PostMapping("add-new-taurus-usage")
    public ResponseEntity<TaurusUsageDTO> addTaurusUsage(@Validated @RequestBody TaurusUsageDTO taurusUsageDTO) {
        return ResponseEntity.ok(taurusUsageService.add(taurusUsageDTO));
    }

    @PutMapping("update-taurus-usage/{id}")
    public ResponseEntity<TaurusUsageDTO> updateTaurusUsage(@Validated @PathVariable Long id, @RequestBody TaurusUsageDTO taurusUsageDTO) {
        return ResponseEntity.status(202).body(this.taurusUsageService.update(id, taurusUsageDTO));
    }

    @GetMapping("all-taurus-by-account/{idAccount}")
    public ResponseEntity<Page<TaurusDTO>> allTaurusByAccount(@Validated @PathVariable Long idAccount, Pageable pageable)
    {
        return ResponseEntity.ok(this.taurusUsageService.allTaurusByAccount(idAccount, pageable));
    }
}
