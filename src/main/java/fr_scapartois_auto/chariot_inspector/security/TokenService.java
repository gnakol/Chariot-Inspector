package fr_scapartois_auto.chariot_inspector.security;

import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import fr_scapartois_auto.chariot_inspector.account.services.AccountService;
import fr_scapartois_auto.chariot_inspector.token.beans.Token;
import fr_scapartois_auto.chariot_inspector.token.repositories.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
            Account utilisateur = (Account) this.accountService.loadUserByUsername(username);
        return this.generateJwt(utilisateur);
    }

    private Map<String, String> generateJwt(Account account) {
        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + 60 * 60 * 1000;

        final Map<String, Object> claims = Map.of(
                "name", account.getName(),
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

    public boolean validateToken(String token) {
        try {
            if (isTokenExpired(token)) {
                return false;
            }

            if (isTokenDisabled(token)) {
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }






}
