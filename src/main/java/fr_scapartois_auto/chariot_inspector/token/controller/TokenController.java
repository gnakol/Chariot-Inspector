package fr_scapartois_auto.chariot_inspector.token.controller;


import fr_scapartois_auto.chariot_inspector.security.TokenService;
import fr_scapartois_auto.chariot_inspector.token.services.BeanTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("token")
public class TokenController {

    private final BeanTokenService beanTokenService;

    private final TokenService tokenService;

    @DeleteMapping("remove-all-token")
    public ResponseEntity<String> removeAllToken()
    {
        this.beanTokenService.removeToken();

        return ResponseEntity.ok("Remove all token was successfully");
    }

    @PostMapping("validate-token")
    public ResponseEntity<Map<String, Boolean>> validateToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        boolean isValid = tokenService.validateToken(token);
        return ResponseEntity.ok(Map.of("isValid", isValid));
    }
}