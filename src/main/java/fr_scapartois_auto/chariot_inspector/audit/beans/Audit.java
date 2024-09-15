package fr_scapartois_auto.chariot_inspector.audit.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr_scapartois_auto.chariot_inspector.accompanied.beans.Accompanied;
import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import fr_scapartois_auto.chariot_inspector.cart.beans.Cart;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "audit")
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_audit")
    private Long idAudit;

    @Column(name = "good_points")
    private String goodPoints;

    @Column(name = "improvement_areas")
    private String improvementAreas;

    @Column(name = "audit_date")
    private LocalDateTime auditTime;

    @Column(name = "status")
    private String status;

    @Column(name = "findings")
    private String findings;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JsonIgnoreProperties({"audits"})
    @JoinColumn(name = "id_account", nullable = true)
    private Account account;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "id_cart", nullable = true)
    private Cart cart;

    @Column(name = "work_session_id")
    private String workSessionId;
}
