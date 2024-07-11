package fr_scapartois_auto.chariot_inspector.taurus_usage.controller;

import fr_scapartois_auto.chariot_inspector.accompanied.beans.taurus.dto.TaurusDTO;
import fr_scapartois_auto.chariot_inspector.taurus_usage.dto.TaurusUsageDTO;
import fr_scapartois_auto.chariot_inspector.taurus_usage.service.TaurusUsageService;
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
@RequestMapping("taurus-usage")
@Slf4j
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

    @DeleteMapping("remove-taurus-usage-by-id/{idTaurusUsage}")
    public ResponseEntity<String> removeTaurusUsage(@Validated @PathVariable Long idTaurusUsage)
    {
        this.taurusUsageService.remove(idTaurusUsage);

        return ResponseEntity.status(202).body("Taurus usage was successfully remove");
    }

    @DeleteMapping("remove-taurus-usage-by-range-id/{startId}/{endId}")
    public ResponseEntity<String> removeTaurusUsageByIdRange(@Validated @PathVariable Long startId, @PathVariable Long endId)
    {
        this.taurusUsageService.removeTaurusUsageByIdRange(startId, endId);

        return ResponseEntity.status(202).body("Remove by range id taurus usage was successfully");
    }

    @DeleteMapping("remove-taurus-usage-by-choose-id")
    public ResponseEntity<String> removeTaurusUsageByChooseId(@Validated @RequestBody List<Long> listIdsTaurusUsage)
    {
        this.taurusUsageService.removeTaurusUsageByChooseId(listIdsTaurusUsage);

        return ResponseEntity.status(202).body("Remove Taurus usage by chosse id was successfully");
    }

    @GetMapping("all-taurus-by-account/{idAccount}")
    public ResponseEntity<Page<TaurusDTO>> allTaurusByAccount(@Validated @PathVariable Long idAccount, Pageable pageable)
    {
        return ResponseEntity.ok(this.taurusUsageService.allTaurusByAccount(idAccount, pageable));
    }

    @GetMapping("get-taurus-usage-by-id-account/{idAccount}")
    public ResponseEntity<Page<TaurusUsageDTO>> getTaurusUsageByAccountId(@Validated @PathVariable Long idAccount, Pageable pageable)
    {
        return ResponseEntity.ok(this.taurusUsageService.getTaurusUsageByAccountId(idAccount, pageable));
    }

    @GetMapping("get-taurus-usage-by-taurus-id/{taurusId}")
    public ResponseEntity<TaurusUsageDTO> getTaurusUsageByTaurusId(@PathVariable Long taurusId) {

        return this.taurusUsageService.getTaurusUsageByTaurusId(taurusId)
                .map(taurusUsageDTO -> {
                    log.info("taurus usage with id :"+taurusId+ " was found");
                    return new ResponseEntity<>(taurusUsageDTO, HttpStatus.OK);
                })
                .orElseThrow(() -> {
                    log.error("taurus usage with id : "+taurusId+ " was not found");
                    throw new RuntimeException("not found this id");
                });
    }
}
