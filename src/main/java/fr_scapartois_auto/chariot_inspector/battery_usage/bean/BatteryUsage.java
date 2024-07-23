package fr_scapartois_auto.chariot_inspector.battery_usage.bean;

import fr_scapartois_auto.chariot_inspector.battery.beans.Battery;
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
@Table(name = "battery_usage")
public class BatteryUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_battery_usage")
    private Long idBatteryUsage;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_cart", nullable = false)
    private Cart cart;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_battery")
    private Battery battery;

    @Column(name = "change_time")
    private LocalDateTime changeTime;

    @Column(name = "charge_level")
    private Long chargeLevel;

    @Column(name = "state")
    private String state;

    @Column(name = "work_session_id")
    private String workSessionId;
}
