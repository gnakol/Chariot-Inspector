package fr_scapartois_auto.chariot_inspector.account_team.controller;

import fr_scapartois_auto.chariot_inspector.account_team.dto.AccountTeamDTO;
import fr_scapartois_auto.chariot_inspector.account_team.service.AccountTeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("account-team")
@Slf4j
@RequiredArgsConstructor
public class AccountTeamController {

    private final AccountTeamService accountTeamService;

    @GetMapping("all-account-team")
    public ResponseEntity<Page<AccountTeamDTO>> allAccountTeam(Pageable pageable)
    {
        return ResponseEntity.ok(this.accountTeamService.all(pageable));
    }

    @PostMapping("add-new-account-team")
    public ResponseEntity<AccountTeamDTO> addNewAccountTeam(@Validated @RequestBody AccountTeamDTO accountTeamDTO)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.accountTeamService.addOrUpdateAccountTeam(accountTeamDTO));
    }

    @GetMapping("get-account-team-by-id/{idAccountTeam}")
    public ResponseEntity<AccountTeamDTO> getAccountTeamById(@Validated @PathVariable Long idAccountTeam)
    {
        return this.accountTeamService.getById(idAccountTeam)
                .map(accountTeamDTO -> {
                    log.info("Account Team with id :" +idAccountTeam+ " was found");
                    return new ResponseEntity<>(accountTeamDTO, HttpStatus.OK);
                })
                .orElseThrow(() -> {
                    log.error("Account Team with id : " +idAccountTeam+ " was not found");
                    throw new RuntimeException("Sorry this id account team not found");
                });
    }
}
