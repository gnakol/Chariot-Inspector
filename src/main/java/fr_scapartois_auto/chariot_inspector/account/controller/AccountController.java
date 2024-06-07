package fr_scapartois_auto.chariot_inspector.account.controller;

import fr_scapartois_auto.chariot_inspector.account.dtos.AccountDTO;
import fr_scapartois_auto.chariot_inspector.account.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("account")
public class AccountController {

    private final AccountService accountService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("all-account")
    public ResponseEntity<Page<AccountDTO>> allAccount(Pageable pageable)
    {
        return ResponseEntity.ok(this.accountService.all(pageable));
    }
}
