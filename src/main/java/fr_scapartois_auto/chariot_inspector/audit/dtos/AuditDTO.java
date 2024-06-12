package fr_scapartois_auto.chariot_inspector.audit.dtos;

import fr_scapartois_auto.chariot_inspector.accompanied.dto.AccompaniedDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditDTO {

    private Long idAudit;
    private String goodPoints;
    private String improvementAreas;
    private String nameAudit;
    private String firstNameAudit;

    private List<AccompaniedDTO> accompaniedDTOS;
}
