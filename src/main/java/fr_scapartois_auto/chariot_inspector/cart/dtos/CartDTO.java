package fr_scapartois_auto.chariot_inspector.cart.dtos;

import fr_scapartois_auto.chariot_inspector.accompanied.dto.AccompaniedDTO;
import fr_scapartois_auto.chariot_inspector.account.dtos.AccountDTO;
import fr_scapartois_auto.chariot_inspector.battery.dtos.BatteryDTO;
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
}
