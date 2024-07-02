package fr_scapartois_auto.chariot_inspector.issue.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IssueDTO {

    private Long idIssue;

    private String description;

    private LocalDate createdAt;

    private Long accountId;
}
