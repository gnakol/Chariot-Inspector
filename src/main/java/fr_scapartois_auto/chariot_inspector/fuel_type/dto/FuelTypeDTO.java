package fr_scapartois_auto.chariot_inspector.fuel_type.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FuelTypeDTO {

    private Long idFuelType;

    private String name;
}
