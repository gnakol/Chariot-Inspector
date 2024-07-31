package fr_scapartois_auto.chariot_inspector.fuel_type.mapper;

import fr_scapartois_auto.chariot_inspector.fuel_type.bean.FuelType;
import fr_scapartois_auto.chariot_inspector.fuel_type.dto.FuelTypeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "default")
public interface FuelTypeMapper {

    FuelTypeDTO fromFuelType(FuelType fuelType);

    FuelType fromFuelTypeDTO(FuelTypeDTO fuelTypeDTO);
}
