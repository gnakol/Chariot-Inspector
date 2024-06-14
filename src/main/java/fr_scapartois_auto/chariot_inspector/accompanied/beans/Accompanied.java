package fr_scapartois_auto.chariot_inspector.accompanied.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr_scapartois_auto.chariot_inspector.audit.beans.Audit;
import fr_scapartois_auto.chariot_inspector.cart.beans.Cart;
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
@Table(name = "accompanied")
public class Accompanied {

    @EmbeddedId
    private AccompaniedKey idAccompanied;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @MapsId("idCart")
    @JoinColumn(name = "id_cart", insertable = false, updatable = false)
    private Cart cart;

    @ManyToOne(cascade = CascadeType.MERGE)
    @MapsId("idAudit")
    @JoinColumn(name = "id_audit", insertable = false, updatable = false)
    private Audit audit;
}
