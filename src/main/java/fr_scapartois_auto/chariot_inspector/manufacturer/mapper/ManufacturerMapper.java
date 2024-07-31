package fr_scapartois_auto.chariot_inspector.manufacturer.mapper;

import fr_scapartois_auto.chariot_inspector.manufacturer.bean.Manufacturer;
import fr_scapartois_auto.chariot_inspector.manufacturer.dto.ManufacturerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "default")
public interface ManufacturerMapper {

    ManufacturerDTO fromManufacturer(Manufacturer manufacturer);

    Manufacturer fromManufacturerDTO(ManufacturerDTO manufacturerDTO);
}
