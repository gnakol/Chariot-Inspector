package fr_scapartois_auto.chariot_inspector.pickup.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PickupDTO {

    private Long idPickup;

    private Long accountId;

    private Long cartId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime pickupDateTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime returnDateTime;

    private String workSessionId;

    private String conditionChassis;
    private String wheelsTornPlat;
    private String batteryCablesSockets;
    private String cleanNonSlipPlatform;
    private String windshield;
    private String gasBlockStrap;
    private String forwardReverseControl;
    private String honk;
    private String functionalElevationSystem;
    private String emergencyStop;
    private String noLeak;
    private String antiCrushButton;
    private String conditionForks;
}
