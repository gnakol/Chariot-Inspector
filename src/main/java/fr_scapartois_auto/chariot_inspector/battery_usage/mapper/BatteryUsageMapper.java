package fr_scapartois_auto.chariot_inspector.battery_usage.mapper;

import fr_scapartois_auto.chariot_inspector.battery.mappers.BatteryMapper;
import fr_scapartois_auto.chariot_inspector.battery_usage.bean.BatteryUsage;
import fr_scapartois_auto.chariot_inspector.battery_usage.dto.BatteryUsageDTO;
import fr_scapartois_auto.chariot_inspector.cart.mappers.CartMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "default", uses = {BatteryMapper.class, CartMapper.class})
public interface BatteryUsageMapper {

    @Mapping(target = "batteryId", source = "battery.idBattery")
    @Mapping(target = "cartId", source = "cart.idCart")
    BatteryUsageDTO fromBatteryUsage(BatteryUsage batteryUsage);

    @Mapping(target = "battery.idBattery", source = "batteryId")
    @Mapping(target = "cart.idCart", source = "cartId")
    BatteryUsage fromBatteryUsageDTO(BatteryUsageDTO batteryUsageDTO);
}
