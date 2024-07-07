package fr_scapartois_auto.chariot_inspector.account.repositories;

import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String username);



}
