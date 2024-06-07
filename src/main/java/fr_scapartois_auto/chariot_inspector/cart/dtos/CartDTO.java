package fr_scapartois_auto.chariot_inspector.cart.dtos;

import fr_scapartois_auto.chariot_inspector.account.dtos.AccountDTO;
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
    private String refCart;
    private Long cartNumber;
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

    private List<AccountDTO> accountDTOS;
}
