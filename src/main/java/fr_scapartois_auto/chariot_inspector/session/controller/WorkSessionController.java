package fr_scapartois_auto.chariot_inspector.session.controller;

import fr_scapartois_auto.chariot_inspector.session.bean.WorkSession;
import fr_scapartois_auto.chariot_inspector.session.service.WorkSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("work-session")
public class WorkSessionController {

    private final WorkSessionService workSessionService;

    @GetMapping("all-work-session")
    public ResponseEntity<Page<WorkSession>> allWorkSession(Pageable pageable)
    {
        return ResponseEntity.ok(this.workSessionService.allWorkSession(pageable));
    }

    @PostMapping("/start-session")
    public ResponseEntity<Map<String, String>> startWorkSession(@RequestBody Map<String, Long> request)
    {
        Long idAccount = request.get("accountId");
        String workSessionId = workSessionService.startNewWorkSession(idAccount);
        Map<String, String> response = new HashMap<>();
        response.put("workSessionId", workSessionId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("end-session")
    public ResponseEntity<Void> endWorkSession(@RequestBody Map<String, String> request) {
        String workSessionId = request.get("workSessionId");
        workSessionService.endWorkSession(workSessionId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("remove-work-session/{idWorkSession}")
    public ResponseEntity<String> removeWorkSession(@Validated @PathVariable Long idWorkSession)
    {
        this.workSessionService.removeSession(idWorkSession);

        return ResponseEntity.status(202).body("Work Session was successfully remove");
    }
}
