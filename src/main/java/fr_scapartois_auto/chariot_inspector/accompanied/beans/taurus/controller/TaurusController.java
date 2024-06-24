package fr_scapartois_auto.chariot_inspector.accompanied.beans.taurus.controller;

import fr_scapartois_auto.chariot_inspector.accompanied.beans.taurus.dto.TaurusDTO;
import fr_scapartois_auto.chariot_inspector.accompanied.beans.taurus.service.TaurusService;
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
@RequestMapping("taurus")
public class TaurusController {

    private final TaurusService taurusService;

    @GetMapping("all-taurus")
    public ResponseEntity<Page<TaurusDTO>> allTaurus(Pageable pageable)
    {
        return ResponseEntity.ok(this.taurusService.all(pageable));
    }

    @PostMapping("add-new-taurus")
    public ResponseEntity<TaurusDTO> addNewTaurus(@Validated @RequestBody TaurusDTO taurusDTO)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.taurusService.add(taurusDTO));
    }

    @GetMapping("get-taurus-by-id/{idTaurus}")
    public ResponseEntity<TaurusDTO> getTaurusById(@Validated @PathVariable Long idTaurus)
    {
        return this.taurusService.getById(idTaurus)
                .map(taurusDTO -> {
                    log.info("taurus with id : " +idTaurus+ " was found");
                    return new ResponseEntity<>(taurusDTO, HttpStatus.OK);
                })
                .orElseThrow(() ->{
                    log.error("taurus with id : " +idTaurus+ " was not found");
                    return new RuntimeException("sorry this taurus was not found");
                });
    }

    @GetMapping("get-id-taurus-by-number/{taurusNumber}")
    public ResponseEntity<Long> getIdTaurusByNumber(@Validated @PathVariable Long taurusNumber)
    {
        Long idTaurus = this.taurusService.getTaurusIdByNumber(taurusNumber);

        return ResponseEntity.ok(idTaurus);
    }
}
