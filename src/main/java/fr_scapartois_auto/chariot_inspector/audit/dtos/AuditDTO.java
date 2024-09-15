package fr_scapartois_auto.chariot_inspector.audit.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class AuditDTO {

    private Long idAudit;

    private String goodPoints;

    private String improvementAreas;

    private LocalDateTime auditTime;

    private String status;

    private String findings;

    private Long accountId;

    private Long cartId;

    private String workSessionId;
}
