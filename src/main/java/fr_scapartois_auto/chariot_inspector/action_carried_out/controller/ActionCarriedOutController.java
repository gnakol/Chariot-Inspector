package fr_scapartois_auto.chariot_inspector.action_carried_out.controller;

import fr_scapartois_auto.chariot_inspector.action_carried_out.beans.ActionCarriedOut;
import fr_scapartois_auto.chariot_inspector.action_carried_out.services.ActionCarriedOutServe;
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

    public final ActionCarriedOutServe actionCarriedOutServe;

    @GetMapping("all-action-carried-out")
    public ResponseEntity<Page<ActionCarriedOut>> allActionCarriedOut(Pageable pageable)
    {
        return ResponseEntity.ok(this.actionCarriedOutServe.all(pageable));
    }

    @PostMapping("add-new-action-carried-out")
    public ResponseEntity<ActionCarriedOut> addNewActionCarriedOut(@Validated @RequestBody ActionCarriedOut actionCarriedOut)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.actionCarriedOutServe.add(actionCarriedOut));
    }

    @PutMapping("update-action-carried-out/{idActionCarriedOut}")
    public ResponseEntity<ActionCarriedOut> updateActionCarriedOut(@Validated @PathVariable Long idActionCarriedOut, @RequestBody ActionCarriedOut actionCarriedOut)
    {
        return ResponseEntity.status(202).body(this.actionCarriedOutServe.update(idActionCarriedOut, actionCarriedOut));
    }

    @DeleteMapping("remove-action-carried-out/{idActionCarriedOut}")
    public ResponseEntity<String> removeActionCarriedOut(@Validated @PathVariable Long idActionCarriedOut)
    {
        this.actionCarriedOutServe.remove(idActionCarriedOut);

        return ResponseEntity.status(202).body("Action carried out with id : " +idActionCarriedOut+ " was successfully remove");
    }

    @GetMapping("get-action-carried-out-by-id/{idActionCarriedOut}")
    public ResponseEntity<ActionCarriedOut> getActionCarriedOutById(@Validated @PathVariable Long idActionCarriedOut)
    {
        return this.actionCarriedOutServe.getById(idActionCarriedOut)
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
