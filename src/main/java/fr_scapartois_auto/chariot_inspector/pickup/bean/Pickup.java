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
}
