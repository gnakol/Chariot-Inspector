package fr_scapartois_auto.chariot_inspector.account_team.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AccountTeamDTO {

    private Long idAccountTeam;

    private Long accountId;

    private Long teamId;

    private Long shiftId;

    private LocalDate startDate;

    private LocalDate endDate;
}
