package fr_scapartois_auto.chariot_inspector.account_service.mapper;

import fr_scapartois_auto.chariot_inspector.account_service.bean.AccountServiceBean;
import fr_scapartois_auto.chariot_inspector.account_service.dto.AccountServiceDTO;
import fr_scapartois_auto.chariot_inspector.ware_house.mapper.WareHouseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "default", uses = {WareHouseMapper.class})
public interface AccountServiceMapper {

    @Mapping(target = "wareHouseId", source = "wareHouse.idWareHouse")
    AccountServiceDTO fromAccountService(AccountServiceBean accountService);

    @Mapping(target = "wareHouse.idWareHouse", source = "wareHouseId")
    AccountServiceBean fromAccountServiceDTO(AccountServiceDTO accountServiceDTO);
}
