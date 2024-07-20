package fr_scapartois_auto.chariot_inspector.issue.dtos;


import fr_scapartois_auto.chariot_inspector.action_carried_out.beans.ActionCarriedOut;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IssueDTO {

    private Long idIssue;

    private String description;

    private LocalDate createdAt;

    private Long accountId;

    private String workSessionId;

    private List<ActionCarriedOut> actionCarriedOuts;
}
