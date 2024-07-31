package fr_scapartois_auto.chariot_inspector.cart.dtos;

import fr_scapartois_auto.chariot_inspector.accompanied.dto.AccompaniedDTO;
import fr_scapartois_auto.chariot_inspector.account.dtos.AccountDTO;
import fr_scapartois_auto.chariot_inspector.battery.dtos.BatteryDTO;
import fr_scapartois_auto.chariot_inspector.cart_category.dto.CartCategoryDTO;
import fr_scapartois_auto.chariot_inspector.fuel_type.dto.FuelTypeDTO;
import fr_scapartois_auto.chariot_inspector.manufacturer.dto.ManufacturerDTO;
import fr_scapartois_auto.chariot_inspector.ware_house.dto.WareHouseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDTO {

    private Long idCart;
    private String cartNumber;

    private String brand;

    private Long manufacturerId;

    private Long categoryId;

    private Long fuelTypeId;
}
