package fr_scapartois_auto.chariot_inspector.battery.dtos;


import fr_scapartois_auto.chariot_inspector.cart.dtos.CartDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BatteryDTO {
    private Long idBattery;
    private String refBattery;
    private Long batteryNumber;
}
