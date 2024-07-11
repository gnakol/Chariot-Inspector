package fr_scapartois_auto.chariot_inspector.session.repositorie;

import fr_scapartois_auto.chariot_inspector.session.bean.WorkSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Modifying
    @Query("DELETE FROM WorkSession t WHERE t.idWorkSession BETWEEN :startId AND :endId")
    void deleteByIdRange(@Param("startId") Long startId, @Param("endId") Long endId);

    // delete by choose id

    @Modifying
    @Query("DELETE FROM WorkSession t WHERE t.idWorkSession IN :ids")
    void deleteByIds(@Param("ids") List<Long> ids);
}
