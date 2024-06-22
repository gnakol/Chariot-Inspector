package fr_scapartois_auto.chariot_inspector.accompanied.beans.taurus.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr_scapartois_auto.chariot_inspector.taurus_usage.bean.TaurusUsage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "taurus")
public class Taurus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_taurus")
    private Long idTaurus;

    @Column(name = "ref_taurus")
    private String refTaurus;

    @Column(name = "taurus_number")
    private Long taurusNumber;

    @OneToMany(mappedBy = "taurus")
    //@JsonIgnoreProperties({"taurus", "account"})
    private List<TaurusUsage> taurusUsages;
}
