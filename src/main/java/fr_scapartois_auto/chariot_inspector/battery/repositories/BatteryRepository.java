package fr_scapartois_auto.chariot_inspector.battery.repositories;

import fr_scapartois_auto.chariot_inspector.battery.beans.Battery;
import fr_scapartois_auto.chariot_inspector.cart.beans.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BatteryRepository extends JpaRepository<Battery, Long> {

    Optional<Battery> findByBatteryNumber(Long batteryNumber);

    List<Battery> findBatteryByCart(Cart cart);

}
