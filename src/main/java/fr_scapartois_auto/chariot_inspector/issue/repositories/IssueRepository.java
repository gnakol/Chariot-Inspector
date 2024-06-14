package fr_scapartois_auto.chariot_inspector.issue.repositories;

import fr_scapartois_auto.chariot_inspector.issue.beans.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {


}
