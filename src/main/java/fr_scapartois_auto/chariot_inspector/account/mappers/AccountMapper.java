package fr_scapartois_auto.chariot_inspector.account.mappers;

import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import fr_scapartois_auto.chariot_inspector.account.dtos.AccountDTO;
import fr_scapartois_auto.chariot_inspector.account_service.mapper.AccountServiceMapper;
import fr_scapartois_auto.chariot_inspector.cart.mappers.CartMapper;
import fr_scapartois_auto.chariot_inspector.issue.mappers.IssueMapper;
import fr_scapartois_auto.chariot_inspector.pickup.mapper.PickupMapper;
import fr_scapartois_auto.chariot_inspector.role.mappers.RoleMapper;
import fr_scapartois_auto.chariot_inspector.taurus_usage.mapper.TaurusUsageMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "default", uses = {RoleMapper.class, TaurusUsageMapper.class, IssueMapper.class, PickupMapper.class, AccountServiceMapper.class})
public interface AccountMapper {

    @Mapping(target = "accountServiceBeanId", source = "accountServiceBean.idAccountService")
    @Mapping(target = "pickupDTOS", source = "pickups")
    @Mapping(target = "issueDTOS", source = "issues")
    @Mapping(target = "taurusUsageDTOS", source = "taurusUsages")
    @Mapping(target = "roleDTOS", source = "roles")
    AccountDTO fromAccount(Account account);

    @Mapping(target = "accountServiceBean.idAccountService", source = "accountServiceBeanId")
    @Mapping(target = "pickups", source = "pickupDTOS")
    @Mapping(target = "issues", source = "issueDTOS")
    @Mapping(target = "taurusUsages", source = "taurusUsageDTOS")
    @Mapping(target = "roles", source = "roleDTOS")
    Account fromAccountDTO(AccountDTO accountDTO);
}
