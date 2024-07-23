package fr_scapartois_auto.chariot_inspector.pickup.bean;

import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import fr_scapartois_auto.chariot_inspector.cart.beans.Cart;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "pickup")
public class Pickup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPickup;

    @ManyToOne
    @JoinColumn(name = "id_account", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "id_cart", nullable = false)
    private Cart cart;

    @Column(name = "pickup_date_time")
    private LocalDateTime pickupDateTime;
    @Column(name = "return_date_time")
    private LocalDateTime returnDateTime;

    @Column(name = "work_session_id")
    private String workSessionId;

    @Column(name = "condition_chassis")
    private String conditionChassis;

    @Column(name = "wheels_torn_plat")
    private String wheelsTornPlat;

    @Column(name = "battery_cables_sockets")
    private String batteryCablesSockets;

    @Column(name = "clean_non_slip_platform")
    private String cleanNonSlipPlatform;

    @Column(name = "windshield")
    private String windshield;

    @Column(name = "gas_block_strap")
    private String gasBlockStrap;

    @Column(name = "forward_reverse_controls")
    private String forwardReverseControl;

    @Column(name = "honk")
    private String honk;

    @Column(name = "functional_elevation_system")
    private String functionalElevationSystem;

    @Column(name = "emergency_stop")
    private String emergencyStop;

    @Column(name = "no_leak")
    private String noLeak;

    @Column(name = "anti_crush_button")
    private String antiCrushButton;

    @Column(name = "condition_forks")
    private String conditionForks;
}
