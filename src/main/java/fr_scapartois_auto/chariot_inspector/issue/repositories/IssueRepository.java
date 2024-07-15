package fr_scapartois_auto.chariot_inspector.issue.repositories;

import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import fr_scapartois_auto.chariot_inspector.issue.beans.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

/*    @Query("SELECT i FROM Issue i JOIN i.account a JOIN a.accountTeams at JOIN at.team t JOIN at.shift s LEFT JOIN ActionCarriedOut ac ON i.idIssue = ac.issue.idIssue WHERE ac.idActionCarriedOut IS NULL AND t.name = :team AND i.createdAt BETWEEN :shiftStart AND :shiftEnd AND at.startDate <= :currentDate AND (at.endDate IS NULL OR at.endDate >= :currentDate)")
    List<Issue> findUnresolvedIssuesByTeamAndShift(@Param("team") String team, @Param("shiftStart") LocalDateTime shiftStart, @Param("shiftEnd") LocalDateTime shiftEnd, @Param("currentDate") LocalDate currentDate);*/

/*    @Query("SELECT i FROM Issue i " +
            "JOIN i.account a " +
            "JOIN a.accountTeams at " +
            "JOIN at.team t " +
            "JOIN at.shift s " +
            "LEFT JOIN ActionCarriedOut ac ON i.idIssue = ac.issue.idIssue " +
            "WHERE ac.idActionCarriedOut IS NULL " +
            "AND t.name = :team " +
            "AND i.createdAt BETWEEN :shiftStart AND :shiftEnd " +
            "AND at.startDate <= :shiftStart " +
            "AND (at.endDate IS NULL OR at.endDate >= :shiftEnd)")
    List<Issue> findUnresolvedIssuesByTeamAndShift(@Param("team") String team,
                                                   @Param("shiftStart") LocalDateTime shiftStart,
                                                   @Param("shiftEnd") LocalDateTime shiftEnd);*/

}
