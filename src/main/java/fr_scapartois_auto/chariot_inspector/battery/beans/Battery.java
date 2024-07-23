package fr_scapartois_auto.chariot_inspector.battery.beans;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr_scapartois_auto.chariot_inspector.cart.beans.Cart;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "battery")
public class Battery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_battery")
    private Long idBattery;

    @Column(name = "ref_battery")
    private String refBattery;

    @Column(name = "battery_number")
    private Long batteryNumber;
}
