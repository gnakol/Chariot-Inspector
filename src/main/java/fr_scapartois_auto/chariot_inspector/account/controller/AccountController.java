package fr_scapartois_auto.chariot_inspector.account.controller;

import fr_scapartois_auto.chariot_inspector.account.dtos.AccountDTO;
import fr_scapartois_auto.chariot_inspector.account.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("account")
public class AccountController {

    private final AccountService accountService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_CHEF_EQUIPE') or hasAuthority('ROLE_AGENT_MAITRISE')")
    @GetMapping("all-account")
    public ResponseEntity<Page<AccountDTO>> allAccount(Pageable pageable)
    {
        return ResponseEntity.ok(this.accountService.all(pageable));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_CHEF_EQUIPE') or hasAuthority('ROLE_AGENT_MAITRISE')")
    @PostMapping(path = "add-new-account", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDTO> addAccount(@Validated @RequestBody AccountDTO accountDTO) throws NoSuchAlgorithmException {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.accountService.addNewAccount(accountDTO));
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_AGENT_MAITRISE')")
    @PutMapping("update-account/{idAccount}")
    public ResponseEntity<AccountDTO> updateAccount(@Validated @PathVariable Long idAccount, @RequestBody AccountDTO accountDTO)
    {
        return ResponseEntity.status(202).body(this.accountService.update(idAccount, accountDTO));
    }

    //@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_CHEF_EQUIPE') or hasAuthority('ROLE_AGENT_MAITRISE')")
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

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_AGENT_MAITRISE')")
    @DeleteMapping("remove-account/{idAccount}")
    public ResponseEntity<String> removeAccount(@Validated @PathVariable Long idAccount)
    {
        this.accountService.remove(idAccount);

        return ResponseEntity.status(202).body("Account id : "+idAccount+ "was successfully remove");
    }

    @GetMapping("get-user-id-by-email")
    public ResponseEntity<Long> getUserIdByEmail(@RequestParam String email) {
        Long userId = accountService.getAccountIdByEmail(email); // Implémentez cette méthode dans votre service
        return ResponseEntity.ok(userId);
    }

}
