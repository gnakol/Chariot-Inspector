package fr_scapartois_auto.chariot_inspector.cart.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr_scapartois_auto.chariot_inspector.accompanied.beans.Accompanied;
import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import fr_scapartois_auto.chariot_inspector.battery.beans.Battery;
import fr_scapartois_auto.chariot_inspector.cart_category.bean.CartCategory;
import fr_scapartois_auto.chariot_inspector.fuel_type.bean.FuelType;
import fr_scapartois_auto.chariot_inspector.manufacturer.bean.Manufacturer;
import fr_scapartois_auto.chariot_inspector.ware_house.bean.WareHouse;
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

    @ManyToOne
    @JoinColumn(name = "id_manufacturer")
    private Manufacturer manufacturer;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private CartCategory category;

    @ManyToOne
    @JoinColumn(name = "id_fuel_type")
    private FuelType fuelType;
}
