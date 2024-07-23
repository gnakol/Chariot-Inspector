package fr_scapartois_auto.chariot_inspector.issue.mappers;

import fr_scapartois_auto.chariot_inspector.account.mappers.AccountMapper;
import fr_scapartois_auto.chariot_inspector.issue.beans.Issue;
import fr_scapartois_auto.chariot_inspector.issue.dtos.IssueDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "default", uses = {AccountMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IssueMapper {

    @Mapping(target = "accountId", source = "account.idAccount")
    IssueDTO fromIssue(Issue issue);

    @Mapping(target = "account.idAccount", source = "accountId")
    Issue fromIssueDTO(IssueDTO issueDTO);
}
