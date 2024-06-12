package fr_scapartois_auto.chariot_inspector.battery.mappers;

import fr_scapartois_auto.chariot_inspector.battery.beans.Battery;
import fr_scapartois_auto.chariot_inspector.battery.dtos.BatteryDTO;
import fr_scapartois_auto.chariot_inspector.cart.mappers.CartMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "default", uses = {CartMapper.class})
public interface BatteryMapper {

    @Mapping(target = "cartDTO", source = "cart")
    BatteryDTO fromBattery(Battery battery);

    @Mapping(target = "cart", source = "cartDTO")
    Battery fromBatteryDTO(BatteryDTO batteryDTO);
}
