package fr_scapartois_auto.chariot_inspector.pickup.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PickupDTO {

    private Long idPickup;

    private Long accountId;

    private Long cartId;

    private LocalDateTime pickupDateTime;

    private LocalDateTime returnDateTime;
}
