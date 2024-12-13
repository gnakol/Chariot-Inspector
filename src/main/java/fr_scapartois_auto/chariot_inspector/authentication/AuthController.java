package fr_scapartois_auto.chariot_inspector.authentication;

import fr_scapartois_auto.chariot_inspector.account.dtos.AccountDTO;
import fr_scapartois_auto.chariot_inspector.account.services.AccountService;
import fr_scapartois_auto.chariot_inspector.security.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;

    private final AccountService accountService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(path = "connexion", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> connexion(@Validated @RequestBody fr_scapartois_auto.chariot_inspector.authentication.bean.Authentication beansRecord) {
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(beansRecord.username(), beansRecord.password())
        );

        if(authenticate.isAuthenticated()) {
            return this.tokenService.generate(beansRecord.username());
        }
        return null;
    }

    @PostMapping("disconnect")
    public ResponseEntity<Map<String, String>> disconnect(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            this.tokenService.invalidateToken(token);
            Map<String, String> response = Map.of("message", "disconnection successful");
            return ResponseEntity.ok(response);
        }

        Map<String, String> response = Map.of("error", "Aucun token fourni");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @PostMapping(path = "refresh-token", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> refreshToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            System.out.println("Refreshing token for request: " + token);

            return tokenService.refreshToken(token);
        }

        throw new RuntimeException("No valid token provided for refresh");
    }




}
