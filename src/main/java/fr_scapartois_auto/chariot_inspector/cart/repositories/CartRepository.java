package fr_scapartois_auto.chariot_inspector.cart.repositories;

import fr_scapartois_auto.chariot_inspector.cart.beans.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
