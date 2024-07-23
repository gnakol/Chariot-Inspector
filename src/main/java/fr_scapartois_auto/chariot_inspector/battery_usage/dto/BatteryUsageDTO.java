package fr_scapartois_auto.chariot_inspector.battery_usage.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import fr_scapartois_auto.chariot_inspector.battery.beans.Battery;
import fr_scapartois_auto.chariot_inspector.cart.beans.Cart;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BatteryUsageDTO {

    private Long idBatteryUsage;

    private Long cartId;

    private Long batteryId;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime changeTime;

    private Long chargeLevel;

    private String state;

    private String workSessionId;
}
