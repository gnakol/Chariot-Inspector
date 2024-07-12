package fr_scapartois_auto.chariot_inspector.action_carried_out.dtos;

import fr_scapartois_auto.chariot_inspector.account.dtos.AccountDTO;
import fr_scapartois_auto.chariot_inspector.issue.dtos.IssueDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActionCarriedOutDTO {

    private Long idActionCarriedOut;

    private String description;

    private Long issueId;

    private Long accountId;

    private String workSessionId;
}
