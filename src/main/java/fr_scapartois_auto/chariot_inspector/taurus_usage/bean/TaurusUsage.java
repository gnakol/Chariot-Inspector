package fr_scapartois_auto.chariot_inspector.taurus_usage.bean;

import fr_scapartois_auto.chariot_inspector.accompanied.beans.taurus.bean.Taurus;
import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "taurus_usage")
public class TaurusUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTaurusUsage;

    @ManyToOne
    @JoinColumn(name = "id_account", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "id_taurus", nullable = false)
    private Taurus taurus;

    private LocalDate usageDate;
}
