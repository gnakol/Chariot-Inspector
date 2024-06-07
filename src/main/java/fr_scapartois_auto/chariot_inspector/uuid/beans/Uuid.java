package fr_scapartois_auto.chariot_inspector.uuid.beans;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "uuid")
public class Uuid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_uuid")
    private Long idUuid;

    @Column(name = "value_uuid")
    private String valueUuid;
}
