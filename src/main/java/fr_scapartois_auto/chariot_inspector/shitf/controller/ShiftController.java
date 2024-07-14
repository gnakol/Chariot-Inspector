package fr_scapartois_auto.chariot_inspector.shitf.controller;

import fr_scapartois_auto.chariot_inspector.shitf.dto.ShiftDTO;
import fr_scapartois_auto.chariot_inspector.shitf.service.ShiftService;
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
@RequestMapping("shift")
public class ShiftController {

    private final ShiftService shiftService;

    @GetMapping("all-shift")
    public ResponseEntity<Page<ShiftDTO>> allShift(Pageable pageable)
    {
        return ResponseEntity.ok(this.shiftService.all(pageable));
    }

    @PostMapping("add-new-shift")
    public ResponseEntity<ShiftDTO> addNewShift(@Validated @RequestBody ShiftDTO shiftDTO)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.shiftService.add(shiftDTO));
    }

    @GetMapping("get-shift-by-id/{idShift}")
    public ResponseEntity<ShiftDTO> getShiftById(@Validated @PathVariable Long idShift)
    {
        return this.shiftService.getById(idShift)
                .map(shiftDTO -> {
                    log.info("Shift with id : " +idShift+ " was found");
                    return new ResponseEntity<>(shiftDTO, HttpStatus.OK);
                })
                .orElseThrow(() -> {
                    log.error("Shift with id :" +idShift+ " was not found");
                    throw new RuntimeException("Sorry this Shift was not found");
                });
    }
}
