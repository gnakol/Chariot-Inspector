package fr_scapartois_auto.chariot_inspector.token.repositories;

import fr_scapartois_auto.chariot_inspector.token.beans.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByValueToken(String token);
}
