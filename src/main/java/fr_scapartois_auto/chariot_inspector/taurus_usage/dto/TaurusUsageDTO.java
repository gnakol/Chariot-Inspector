package fr_scapartois_auto.chariot_inspector.taurus_usage.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaurusUsageDTO {

    private Long idTaurusUsage;
    private Long accountId;
    private Long taurusId;

    private LocalDateTime usageDate;
    private String workSessionId;
}
