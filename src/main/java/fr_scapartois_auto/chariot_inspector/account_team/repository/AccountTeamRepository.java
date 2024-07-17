package fr_scapartois_auto.chariot_inspector.account_team.repository;

import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import fr_scapartois_auto.chariot_inspector.account_team.bean.AccountTeam;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AccountTeamRepository extends JpaRepository<AccountTeam, Long> {

    @Transactional
    @Query("SELECT at FROM AccountTeam at WHERE at.account.idAccount = :accountId AND :currentDate BETWEEN at.startDate AND at.endDate")
    Optional<AccountTeam> findByAccountIdAndCurrentDate(@Param("accountId") Long accountId, @Param("currentDate") LocalDate currentDate);

    Optional<AccountTeam> findByWorkSessionId(String workSessionId);

    Optional<AccountTeam> findByAccount(Account account);
}
