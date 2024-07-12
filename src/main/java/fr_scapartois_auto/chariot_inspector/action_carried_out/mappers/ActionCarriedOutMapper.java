package fr_scapartois_auto.chariot_inspector.action_carried_out.mappers;

import fr_scapartois_auto.chariot_inspector.account.mappers.AccountMapper;
import fr_scapartois_auto.chariot_inspector.action_carried_out.beans.ActionCarriedOut;
import fr_scapartois_auto.chariot_inspector.action_carried_out.dtos.ActionCarriedOutDTO;
import fr_scapartois_auto.chariot_inspector.issue.mappers.IssueMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "default", uses = {AccountMapper.class, IssueMapper.class})
public interface ActionCarriedOutMapper {

    @Mapping(target = "issueId", source = "issue.idIssue")
    @Mapping(target = "accountId", source = "account.idAccount")
    ActionCarriedOutDTO fromActionCarriedOut(ActionCarriedOut actionCarriedOut);

    @Mapping(target = "issue.idIssue", source = "issueId")
    @Mapping(target = "account.idAccount", source = "accountId")
    ActionCarriedOut fromActionCarriedOutDTO(ActionCarriedOutDTO actionCarriedOutDTO);


    /*
    *@Mapper(componentModel = "default", uses = {AccountMapper.class})
    public interface IssueMapper {

    @Mapping(target = "accountId", source = "account.idAccount")
    IssueDTO fromIssue(Issue issue);

    @Mapping(target = "account.idAccount", source = "accountId")
    Issue fromIssueDTO(IssueDTO issueDTO);
}*/
}
