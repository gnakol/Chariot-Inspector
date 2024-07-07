package fr_scapartois_auto.chariot_inspector.taurus_usage.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TaurusUsageDTO {

    private Long idTaurusUsage;
    private Long accountId;
    private Long taurusId;
    private LocalDate usageDate;

    private String workSessionId;
}
