package fr_scapartois_auto.chariot_inspector.token.controller;


import fr_scapartois_auto.chariot_inspector.token.services.BeanTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("token")
public class TokenController {

    private final BeanTokenService beanTokenService;

    @DeleteMapping("remove-all-token")
    public ResponseEntity<String> removeAllToken()
    {
        this.beanTokenService.removeToken();

        return ResponseEntity.ok("Remove all token was successfully");
    }
}