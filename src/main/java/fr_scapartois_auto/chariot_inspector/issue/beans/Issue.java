package fr_scapartois_auto.chariot_inspector.issue.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import fr_scapartois_auto.chariot_inspector.action_carried_out.beans.ActionCarriedOut;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "issue")
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_issue")
    private Long idIssue;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "id_account", nullable = true)
    @JsonIgnoreProperties({"actionCarriedOuts", "roles", "password", "issues", "pickups", "taurusUsages"})
    private Account account;

    @Column(name = "work_session_id")
    private String workSessionId;

    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"issue", "account"})
    private List<ActionCarriedOut> actionCarriedOuts;

}
