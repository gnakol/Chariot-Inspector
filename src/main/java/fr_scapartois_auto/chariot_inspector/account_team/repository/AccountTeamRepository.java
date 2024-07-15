package fr_scapartois_auto.chariot_inspector.account_team.repository;

import fr_scapartois_auto.chariot_inspector.account_team.bean.AccountTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTeamRepository extends JpaRepository<AccountTeam, Long> {
}
