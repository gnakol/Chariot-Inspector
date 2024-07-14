package fr_scapartois_auto.chariot_inspector.team.mapper;

import fr_scapartois_auto.chariot_inspector.team.bean.Team;
import fr_scapartois_auto.chariot_inspector.team.dto.TeamDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "default")
public interface TeamMapper {

    TeamDTO fromTeam(Team team);

    Team fromTeamDTO(TeamDTO teamDTO);
}
