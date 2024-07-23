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

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime usageDate;
    private String workSessionId;

/*    @JsonCreator
    public TaurusUsageDTO(
            @JsonProperty("idTaurusUsage") Long idTaurusUsage,
            @JsonProperty("accountId") Long accountId,
            @JsonProperty("taurusId") Long taurusId,
            @JsonProperty("usageDate") LocalDateTime usageDate,
            @JsonProperty("workSessionId") String workSessionId) {
        this.idTaurusUsage = idTaurusUsage;
        this.accountId = accountId;
        this.taurusId = taurusId;
        this.usageDate = usageDate;
        this.workSessionId = workSessionId;
    }*/
}
