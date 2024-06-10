package fr_scapartois_auto.chariot_inspector.account.controller;

import fr_scapartois_auto.chariot_inspector.account.dtos.AccountDTO;
import fr_scapartois_auto.chariot_inspector.account.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("update-account/{idAccount}")
    public ResponseEntity<AccountDTO> updateAccount(@Validated @PathVariable Long idAccount, @RequestBody AccountDTO accountDTO)
    {
        return ResponseEntity.status(202).body(this.accountService.update(idAccount, accountDTO));
    }

    @GetMapping("get-account-by-id/{idAccount}")
    public ResponseEntity<AccountDTO> getByIdAccount(@Validated @PathVariable Long idAccount)
    {
        return this.accountService.getById(idAccount)
                .map(accountDTO -> {
                    log.info("account with id : " +idAccount+ " was found");
                    return new ResponseEntity<>(accountDTO, HttpStatus.OK);
                })
                .orElseThrow(() -> {
                    log.error("account with id : " +idAccount+ " was found");
                    throw new RuntimeException("was not found this id");
                });
    }
}
