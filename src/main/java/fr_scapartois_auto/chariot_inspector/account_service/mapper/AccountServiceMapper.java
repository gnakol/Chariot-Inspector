package fr_scapartois_auto.chariot_inspector.account_service.mapper;

import fr_scapartois_auto.chariot_inspector.account_service.bean.AccountServiceBean;
import fr_scapartois_auto.chariot_inspector.account_service.dto.AccountServiceDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "default")
public interface AccountServiceMapper {

    AccountServiceDTO fromAccountService(AccountServiceBean accountService);

    AccountServiceBean fromAccountServiceDTO(AccountServiceDTO accountServiceDTO);
}
