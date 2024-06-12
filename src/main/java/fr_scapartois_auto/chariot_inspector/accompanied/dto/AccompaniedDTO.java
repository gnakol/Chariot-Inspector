package fr_scapartois_auto.chariot_inspector.accompanied.dto;

import fr_scapartois_auto.chariot_inspector.audit.dtos.AuditDTO;
import fr_scapartois_auto.chariot_inspector.cart.dtos.CartDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccompaniedDTO {

    private Long idCart;

    private Long idAudit;

    private CartDTO cartDTO;

    private AuditDTO auditDTO;
}
