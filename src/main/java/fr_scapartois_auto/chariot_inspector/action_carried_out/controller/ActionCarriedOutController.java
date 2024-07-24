package fr_scapartois_auto.chariot_inspector.action_carried_out.controller;

import fr_scapartois_auto.chariot_inspector.action_carried_out.beans.ActionCarriedOut;
import fr_scapartois_auto.chariot_inspector.action_carried_out.dtos.ActionCarriedOutDTO;
import fr_scapartois_auto.chariot_inspector.action_carried_out.services.ActionCarriedOutService;
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
@RequestMapping("action-carried-out")
public class ActionCarriedOutController {

    public final ActionCarriedOutService actionCarriedOutService;

    @GetMapping("all-action-carried-out")
    public ResponseEntity<Page<ActionCarriedOutDTO>> allActionCarriedOut(Pageable pageable)
    {
        return ResponseEntity.ok(this.actionCarriedOutService.all(pageable));
    }

    @PostMapping("add-new-action-carried-out")
    public ResponseEntity<ActionCarriedOutDTO> addNewActionCarriedOut(@Validated @RequestBody ActionCarriedOutDTO actionCarriedOut)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.actionCarriedOutService.add(actionCarriedOut));
    }

    @PutMapping("update-action-carried-out/{idActionCarriedOut}")
    public ResponseEntity<ActionCarriedOutDTO> updateActionCarriedOut(@Validated @PathVariable Long idActionCarriedOut, @RequestBody ActionCarriedOutDTO actionCarriedOut)
    {
        return ResponseEntity.status(202).body(this.actionCarriedOutService.update(idActionCarriedOut, actionCarriedOut));
    }

    @DeleteMapping("remove-action-carried-out/{idActionCarriedOut}")
    public ResponseEntity<String> removeActionCarriedOut(@PathVariable Long idActionCarriedOut) {
        try {
            actionCarriedOutService.remove(idActionCarriedOut);
            return ResponseEntity.status(HttpStatus.OK).body("Action carried out removed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error removing action carried out");
        }
    }


    @GetMapping("get-action-carried-out-by-id/{idActionCarriedOut}")
    public ResponseEntity<ActionCarriedOutDTO> getActionCarriedOutById(@Validated @PathVariable Long idActionCarriedOut)
    {
        return this.actionCarriedOutService.getById(idActionCarriedOut)
                .map(actionCarriedOut -> {
                    log.info("Action carried with id : " +idActionCarriedOut+ " was found");
                    return new ResponseEntity<>(actionCarriedOut, HttpStatus.OK);
                })
                .orElseThrow(() -> {
                    log.error("Action carried out with id : " +idActionCarriedOut+ " was not found");
                    throw new RuntimeException("sorry this id is nont found");
                });
    }
}
