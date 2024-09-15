package fr_scapartois_auto.chariot_inspector.taurus.dto;

import fr_scapartois_auto.chariot_inspector.taurus_usage.dto.TaurusUsageDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TaurusDTO {

    private Long idTaurus;

    private String refTaurus;

    private Long taurusNumber;

    private List<TaurusUsageDTO> taurusUsageDTOS;

}
