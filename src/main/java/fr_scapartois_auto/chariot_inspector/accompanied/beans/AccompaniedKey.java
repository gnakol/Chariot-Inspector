package fr_scapartois_auto.chariot_inspector.accompanied.beans;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccompaniedKey implements Serializable {

    @Column(name = "id_cart")
    private Long idCart;

    @Column(name = "id_audit")
    private Long idAudit;
}
