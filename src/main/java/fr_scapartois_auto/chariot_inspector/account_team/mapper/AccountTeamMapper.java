package fr_scapartois_auto.chariot_inspector.account_team.mapper;

import fr_scapartois_auto.chariot_inspector.account.mappers.AccountMapper;
import fr_scapartois_auto.chariot_inspector.account_team.bean.AccountTeam;
import fr_scapartois_auto.chariot_inspector.account_team.dto.AccountTeamDTO;
import fr_scapartois_auto.chariot_inspector.shitf.mapper.ShiftMapper;
import fr_scapartois_auto.chariot_inspector.team.mapper.TeamMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "default", uses = {AccountMapper.class, TeamMapper.class, ShiftMapper.class})
public interface AccountTeamMapper {

    @Mapping(target = "accountId", source = "account.idAccount")
    @Mapping(target = "teamId", source = "team.idTeam")
    @Mapping(target = "shiftId", source = "shift.idShift")
    AccountTeamDTO fromAccountTeam(AccountTeam accountTeam);

    @Mapping(target = "account.idAccount", source = "accountId")
    @Mapping(target = "team.idTeam", source = "teamId")
    @Mapping(target = "shift.idShift", source = "shiftId")
    AccountTeam fromAccountTeamDTO(AccountTeamDTO accountTeamDTO);
}
