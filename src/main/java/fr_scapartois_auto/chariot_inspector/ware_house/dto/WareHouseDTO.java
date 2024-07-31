package fr_scapartois_auto.chariot_inspector.ware_house.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WareHouseDTO {

    private Long idWareHouse;

    private String name;
}
