package fr_scapartois_auto.chariot_inspector.taurus_usage.dto;

import fr_scapartois_auto.chariot_inspector.taurus.dto.TaurusDTO;
import fr_scapartois_auto.chariot_inspector.account.dtos.AccountDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TaurusUsageDetailDTO {

    private Long idTaurusUsage;

    private AccountDTO accountDTO;

    private TaurusDTO taurusDTO;

    private LocalDate usageDate;
}
