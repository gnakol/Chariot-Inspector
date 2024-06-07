package fr_scapartois_auto.chariot_inspector.action_carried_out.beans;

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
@Table(name = "action_carried_out")
public class ActionCarriedOut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_action_carried_out")
    private Long idActionCarriedOut;

    @Column(name = "description")
    private String description;
}
