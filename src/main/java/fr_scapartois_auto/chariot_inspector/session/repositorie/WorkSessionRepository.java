package fr_scapartois_auto.chariot_inspector.session.repositorie;

import fr_scapartois_auto.chariot_inspector.session.bean.WorkSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkSessionRepository extends JpaRepository<WorkSession, Long> {

    @Query("SELECT ws FROM WorkSession ws WHERE ws.accountId = :accountId AND ws.endTime IS NULL")
    List<WorkSession> findActiveWorkSessionsByAccountId(@Param("accountId") Long accountId);

    Optional<WorkSession> findByWorkSessionId(String workSessionId);
}
