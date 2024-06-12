package fr_scapartois_auto.chariot_inspector.audit.beans;

import fr_scapartois_auto.chariot_inspector.accompanied.beans.Accompanied;
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

    @Column(name = "nom")
    private String nameAudit;

    @Column(name = "prenom")
    private String firstNameAudit;

    @OneToMany(mappedBy = "audit")
    private List<Accompanied> accompanieds;
}
