package fr_scapartois_auto.chariot_inspector.issue.mappers;

import fr_scapartois_auto.chariot_inspector.account.mappers.AccountMapper;
import fr_scapartois_auto.chariot_inspector.issue.beans.Issue;
import fr_scapartois_auto.chariot_inspector.issue.dtos.IssueDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "default", uses = {AccountMapper.class})
public interface IssueMapper {

    @Mapping(target = "accountId", source = "account.idAccount")
    IssueDTO fromIssue(Issue issue);

    @Mapping(target = "account.idAccount", source = "accountId")
    Issue fromIssueDTO(IssueDTO issueDTO);
}
