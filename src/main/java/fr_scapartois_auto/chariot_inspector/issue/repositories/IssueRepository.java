package fr_scapartois_auto.chariot_inspector.issue.repositories;

import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import fr_scapartois_auto.chariot_inspector.issue.beans.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

    List<Issue> findByAccount(Account account);

    List<Issue> findByWorkSessionId(String workSessionId);

    @Query("SELECT DISTINCT i.workSessionId FROM Issue i WHERE i.account.id = :accountId")
    List<String> findDistinctWorkSessionIdsByAccountId(@Param("accountId") Long accountId);


}
