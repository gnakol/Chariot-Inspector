package fr_scapartois_auto.chariot_inspector.account.mappers;

import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import fr_scapartois_auto.chariot_inspector.account.dtos.AccountDTO;
import fr_scapartois_auto.chariot_inspector.cart.mappers.CartMapper;
import fr_scapartois_auto.chariot_inspector.role.mappers.RoleMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "default", uses = {CartMapper.class, RoleMapper.class})
public interface AccountMapper {

    @Mapping(target = "roleDTOS", source = "roles")
    @Mapping(target = "cartDTO", source = "cart")
    AccountDTO fromAccount(Account account);

    @Mapping(target = "roles", source = "roleDTOS")
    @Mapping(target = "cart", source = "cartDTO")
    Account fromAccountDTO(AccountDTO accountDTO);
}
