package fr_scapartois_auto.chariot_inspector.audit.controller;

import fr_scapartois_auto.chariot_inspector.audit.dtos.AuditDTO;
import fr_scapartois_auto.chariot_inspector.audit.services.AuditService;
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
@RequestMapping("audit")
public class AuditController {

    private final AuditService auditService;

    @GetMapping("all-audit")
    public ResponseEntity<Page<AuditDTO>> allAudit(Pageable pageable)
    {
        return ResponseEntity.ok(this.auditService.all(pageable));
    }

    @PostMapping("add-new-audit")
    public ResponseEntity<AuditDTO> addNewAudit(@Validated @RequestBody AuditDTO auditDTO)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.auditService.add(auditDTO));
    }

    @PutMapping("update-audit/{idAudit}")
    public ResponseEntity<AuditDTO> updateAudit(@Validated @PathVariable Long idAudit, @RequestBody AuditDTO auditDTO)
    {
        return ResponseEntity.status(202).body(this.auditService.update(idAudit, auditDTO));
    }

    @DeleteMapping("remove-audit/{idAudit}")
    public ResponseEntity<String> removeAudit(@Validated @PathVariable Long idAudit)
    {
        this.auditService.remove(idAudit);

        return ResponseEntity.status(202).body("Audit with id : "+idAudit+ " was successfully delete");
    }

    @GetMapping("get-audit-by-id/{idAudit}")
    public ResponseEntity<AuditDTO> getAuditById(@Validated @PathVariable Long idAudit)
    {
        return this.auditService.getById(idAudit)
                .map(auditDTO -> {
                    log.info("Audit with id : " +idAudit+ " was found");
                    return new ResponseEntity<>(auditDTO, HttpStatus.OK);
                })
                .orElseThrow(() -> {
                    log.error("Audit with id : " +idAudit+ " was not fount");
                    throw new RuntimeException("sorry this id : " +idAudit+ "was lost");
                });
    }
}
