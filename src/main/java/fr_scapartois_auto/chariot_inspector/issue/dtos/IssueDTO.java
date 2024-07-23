package fr_scapartois_auto.chariot_inspector.issue.dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import fr_scapartois_auto.chariot_inspector.action_carried_out.beans.ActionCarriedOut;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IssueDTO {

    private Long idIssue;

    private String description;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    private Long accountId;

    private String workSessionId;

    private List<ActionCarriedOut> actionCarriedOuts;
}
