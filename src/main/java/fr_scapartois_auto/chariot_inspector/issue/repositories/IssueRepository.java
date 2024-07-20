package fr_scapartois_auto.chariot_inspector.issue.repositories;

import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import fr_scapartois_auto.chariot_inspector.issue.beans.Issue;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

    List<Issue> findByAccount(Account account);

    List<Issue> findByWorkSessionId(String workSessionId);

    @Query("SELECT DISTINCT i.workSessionId FROM Issue i WHERE i.account.id = :accountId")
    List<String> findDistinctWorkSessionIdsByAccountId(@Param("accountId") Long accountId);

    @Modifying
    @Query("DELETE FROM Issue t WHERE t.idIssue BETWEEN :startId AND :endId")
    void deleteByIdRange(@Param("startId") Long startId, @Param("endId") Long endId);

    // delete by choose id

    @Modifying
    @Query("DELETE FROM Issue t WHERE t.idIssue IN :ids")
    void deleteByIds(@Param("ids") List<Long> ids);

    @Query("SELECT i FROM Issue i WHERE i.description IS NOT NULL AND i.description <> 'RAS'")
    Page<Issue> findIssuesWithDescription(Pageable pageable);

    // all issue with not action carried out and not content RAS
    @Query("SELECT i FROM Issue i LEFT JOIN i.actionCarriedOuts a WHERE a.idActionCarriedOut IS NULL AND i.description != 'RAS'")
    Page<Issue> findIssuesWithoutActions(Pageable pageable);

    @Query("SELECT i FROM Issue i " +
            "JOIN AccountTeam at ON i.account.idAccount = at.account.idAccount " +
            "JOIN Shift s ON at.shift.idShift = s.idShift " +
            "WHERE at.team.idTeam = :teamId " +
            "AND at.startDate <= :endDate " +
            "AND (at.endDate IS NULL OR at.endDate >= :startDate) " +
            "AND s.startTime <= :currentTime " +
            "AND s.endTime >= :currentTime " +
            "AND i.actionCarriedOuts IS EMPTY " +
            "AND i.description <> 'RAS'")
    Page<Issue> findIssuesWithoutActionsByTeamAndDateAndTime(@Param("teamId") Long teamId,
                                                             @Param("startDate") LocalDate startDate,
                                                             @Param("endDate") LocalDate endDate,
                                                             @Param("currentTime") LocalTime currentTime,
                                                             Pageable pageable);






}

