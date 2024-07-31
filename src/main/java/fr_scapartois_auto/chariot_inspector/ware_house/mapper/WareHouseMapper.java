package fr_scapartois_auto.chariot_inspector.ware_house.mapper;

import fr_scapartois_auto.chariot_inspector.ware_house.bean.WareHouse;
import fr_scapartois_auto.chariot_inspector.ware_house.dto.WareHouseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "default")
public interface WareHouseMapper {

    WareHouseDTO fromWareHouse(WareHouse wareHouse);

    WareHouse fromWareHouseDTO(WareHouseDTO wareHouseDTO);
}
