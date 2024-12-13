package fr_scapartois_auto.chariot_inspector.token.controller;


import fr_scapartois_auto.chariot_inspector.security.TokenService;
import fr_scapartois_auto.chariot_inspector.token.dtos.TokenDTO;
import fr_scapartois_auto.chariot_inspector.token.services.BeanTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("token")
public class TokenController {

    private final BeanTokenService beanTokenService;

    private final TokenService tokenService;

    @GetMapping("all-token")
    public ResponseEntity<Page<TokenDTO>> allToken(Pageable pageable)
    {
        return ResponseEntity.ok(this.beanTokenService.allToken(pageable));
    }

    @DeleteMapping("remove-all-token")
    public ResponseEntity<String> removeAllToken()
    {
        //this.beanTokenService.removeToken();

        return ResponseEntity.ok("Remove all token was successfully");
    }

    @PostMapping("validate-token")
    public ResponseEntity<Map<String, Boolean>> validateToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        boolean isValid = tokenService.validateToken(token);
        return ResponseEntity.ok(Map.of("isValid", isValid));
    }

    @DeleteMapping("remove-token-by-id/{idToken}")
    public ResponseEntity<String> removeTokenById(@Validated @PathVariable Long idToken)
    {
        this.beanTokenService.removeTokenById(idToken);

        return ResponseEntity.ok("Token was successfully remove");
    }

    @DeleteMapping("remove-token-by-range/{idToken}")
    public ResponseEntity<String> removeTokenByRange(@Validated @PathVariable Long startId, @PathVariable Long endId)
    {
        this.beanTokenService.removeTokenByRange(startId, endId);

        return ResponseEntity.ok("Token was successfully remove by range");
    }

    @DeleteMapping("remove-token-by-choose-id")
    public ResponseEntity<String> removeTokenById(@Validated @RequestBody List<Long> ids)
    {
        this.beanTokenService.removeTokenByCheckId(ids);

        return ResponseEntity.ok("Token was successfully remove by check id");
    }
}