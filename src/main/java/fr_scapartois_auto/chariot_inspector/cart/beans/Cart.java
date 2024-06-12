package fr_scapartois_auto.chariot_inspector.cart.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr_scapartois_auto.chariot_inspector.accompanied.beans.Accompanied;
import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import fr_scapartois_auto.chariot_inspector.battery.beans.Battery;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cart")
    private Long idCart;

    @Column(name = "ref_cart")
    private String refCart;

    @Column(name = "cart_number")
    private Long cartNumber;

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

    @OneToMany(mappedBy = "cart")
    @JsonIgnoreProperties({"cart"})
    private List<Account> accounts;

    @OneToMany(mappedBy = "cart")
    @JsonIgnoreProperties({"cart"})
    private List<Battery> batteryList;

    @OneToMany(mappedBy = "cart")
    private List<Accompanied> accompanieds;
}
