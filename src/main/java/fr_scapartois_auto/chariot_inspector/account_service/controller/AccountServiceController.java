package fr_scapartois_auto.chariot_inspector.account_service.controller;

import fr_scapartois_auto.chariot_inspector.account_service.dto.AccountServiceDTO;
import fr_scapartois_auto.chariot_inspector.account_service.service.AccountServiceBeanService;
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
@RequestMapping("account-service-bean")
public class AccountServiceController {

    private final AccountServiceBeanService accountServiceBeanService;

    @GetMapping("all-account-service-bean")
    public ResponseEntity<Page<AccountServiceDTO>> allAccountServiceBean(Pageable pageable)
    {
        return ResponseEntity.ok(this.accountServiceBeanService.all(pageable));
    }

    @PostMapping("add-new-account-service-bean")
    public ResponseEntity<AccountServiceDTO> addNewAccountServiceBean(@Validated @RequestBody AccountServiceDTO accountServiceDTO)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.accountServiceBeanService.add(accountServiceDTO));
    }

    @PutMapping("update-account-service/{idAccountService}")
    public ResponseEntity<AccountServiceDTO> updateAccountService(@Validated @PathVariable Long idAccountService, @RequestBody AccountServiceDTO accountServiceDTO)
    {
        return ResponseEntity.status(202).body(this.accountServiceBeanService.update(idAccountService, accountServiceDTO));
    }

    @DeleteMapping("remove-account-service-bean/{idAccountService}")
    public ResponseEntity<String> removeAccountService(@Validated @PathVariable Long idAccountService)
    {
        this.accountServiceBeanService.remove(idAccountService);

        return ResponseEntity.status(202).body("Account service bean was successfully remove");
    }

    @GetMapping("get-account-service-bean-by-id/{idAccountServiceBean}")
    public ResponseEntity<AccountServiceDTO> getAccountServiceById(@Validated @PathVariable Long idAccountServiceBean)
    {
        return this.accountServiceBeanService.getById(idAccountServiceBean)
                .map(accountServiceDTO -> {
                    log.info("Account service bean with id : " +idAccountServiceBean+ " was found");
                    return new ResponseEntity<>(accountServiceDTO, HttpStatus.OK);
                })
                .orElseThrow(() -> {
                    log.error("Account service bean with id : " +idAccountServiceBean+ " was not found");
                    throw new RuntimeException(" Sorry this id account service bean not found");
                });
    }

    @GetMapping("get-id-service-bean-by-name")
    public ResponseEntity<Long> getIdAccountServiceBeanByName(@RequestParam String wareHouseName)
    {
        return ResponseEntity.ok(this.accountServiceBeanService.getIdWareHouseByName(wareHouseName));
    }
}
