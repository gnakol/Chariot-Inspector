package fr_scapartois_auto.chariot_inspector.historical.controller;

import fr_scapartois_auto.chariot_inspector.historical.bean.HistoryEntryDTO;
import fr_scapartois_auto.chariot_inspector.historical.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("history")
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping("all-history/{idAccount}")
    public ResponseEntity<Page<HistoryEntryDTO>> getHistory(@PathVariable Long idAccount, Pageable pageable)
    {
        return ResponseEntity.ok(this.historyService.getHistory(idAccount, pageable));
    }
}
