package fr_scapartois_auto.chariot_inspector.manufacturer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ManufacturerDTO {

    private Long idManufacturer;

    private String name;
}
