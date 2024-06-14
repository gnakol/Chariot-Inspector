package fr_scapartois_auto.chariot_inspector.issue.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToOne
    @JoinColumn(name = "id_account", nullable = true)
    @JsonIgnoreProperties({"issues", "roles", "password"})
    private Account account;

}
