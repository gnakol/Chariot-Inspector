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

    @Column(name = "cart_number")
    private String cartNumber;

    @Column(name = "brand")
    private String brand;
}
