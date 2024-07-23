package fr_scapartois_auto.chariot_inspector.battery.mappers;

import fr_scapartois_auto.chariot_inspector.battery.beans.Battery;
import fr_scapartois_auto.chariot_inspector.battery.dtos.BatteryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "default")
public interface BatteryMapper {

    BatteryDTO fromBattery(Battery battery);

    Battery fromBatteryDTO(BatteryDTO batteryDTO);
}
