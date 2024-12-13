package fr_scapartois_auto.chariot_inspector.security;

import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import fr_scapartois_auto.chariot_inspector.account.services.AccountService;
import fr_scapartois_auto.chariot_inspector.token.beans.Token;
import fr_scapartois_auto.chariot_inspector.token.repositories.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class TokenService {

    private AccountService accountService;

    private final String ENCRYPTION_KEY = "48c9ebea798ae8ac832298911c4a973426ae6bcaef745d70b1aa0ad69914140c";

    private TokenRepository tokenRepository;

    public Map<String, String> generate(String username) {
            Account account = (Account) this.accountService.loadUserByUsername(username);
        return this.generateJwt(account);
    }

    private Map<String, String> generateJwt(Account account) {
        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + 3 * 60 * 1000;

        System.out.println("*******************Generating token for account: " + account.getEmail());
        System.out.println("*******************Token expiration time: " + new Date(expirationTime));

        final Map<String, Object> claims = Map.of(
                "name", account.getName(),
                "roles", account.getRoles(),
                Claims.EXPIRATION, new Date(expirationTime),
                Claims.SUBJECT, account.getEmail()
        );

        final String bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(account.getEmail())
                .setClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

        Token tokenBean = Token.builder()
                .valueToken(bearer)
                .statusToken(false)
                .expirationToken(false)
                .account(account)
                .build();
        System.out.println("Saving new token: " + bearer);
        this.tokenRepository.save(tokenBean);
        return Map.of("bearer", bearer);
    }


    private <T> T getClaim(String token, Function<Claims, T> function) {
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.getKey())
                .setAllowedClockSkewSeconds(60)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getKey() {
        final byte[] decoder = Decoders.BASE64.decode(ENCRYPTION_KEY);
        return Keys.hmacShaKeyFor(decoder);
    }

    public String extractUsername(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return this.getClaim(token, Claims::getExpiration);
    }

    public void invalidateToken(String token)
    {
        String username = this.extractUsername(token);

        Token tokenBean = this.tokenRepository.findByValueToken(token).get();

        if (!tokenBean.getExpirationToken())
        {
            tokenBean.setStatusToken(true);
            this.tokenRepository.save(tokenBean);
        }
    }

    public boolean isTokenDisabled(String token)
    {
        Token tokenBean = this.tokenRepository.findByValueToken(token).get();

        return tokenBean.getStatusToken();
    }

    public Page<Token> allToken(Pageable pageable)
    {
        return this.tokenRepository.findAll(pageable);
    }

    public void deleteToken(Long id)
    {
        Optional<Token> tokenBean = this.tokenRepository.findById(id);

        if (tokenBean.isEmpty())

            throw new RuntimeException("token with id : " +id+ " not found");

        this.tokenRepository.delete(tokenBean.get());


    }

    public boolean isTokenExpiringSoon(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        // Vérifier si le token expire dans les 2 minutes (120000ms)
        long currentTime = System.currentTimeMillis();
        return expirationDate.getTime() - currentTime <= 2 * 60 * 1000;  // 2 minutes avant expiration
    }


    public boolean validateToken(String token) {
        try {
            if (isTokenExpired(token)) {
                System.out.println("Token is expired.");
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token has expired");
            }

            if (isTokenDisabled(token)) {
                System.out.println("Token is disabled.");
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token is disabled");
            }

            System.out.println("Token is valid.");
            return true;
        } catch (Exception e) {
            System.out.println("Token validation failed: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }
    }





    public Map<String, String> refreshToken(String token) {
        try {
            // Essayer d'extraire l'utilisateur même si le token est expiré
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(this.getKey())
                    .setAllowedClockSkewSeconds(60) // Tolérer une petite dérive
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            Account account = (Account) accountService.loadUserByUsername(username);

            // Générer un nouveau token
            return generateJwt(account);
        } catch (ExpiredJwtException e) {
            // Si le token est expiré, on ne déclenche pas d'exception supplémentaire
            throw new RuntimeException("Token expired, cannot refresh");
        } catch (Exception e) {
            throw new RuntimeException("Token invalid or expired, cannot refresh", e);
        }
    }





}
