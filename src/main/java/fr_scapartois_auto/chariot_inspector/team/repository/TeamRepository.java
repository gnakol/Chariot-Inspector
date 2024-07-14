package fr_scapartois_auto.chariot_inspector.team.repository;

import fr_scapartois_auto.chariot_inspector.team.bean.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
}
