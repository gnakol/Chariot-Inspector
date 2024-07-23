package fr_scapartois_auto.chariot_inspector.battery_usage.repositorie;

import fr_scapartois_auto.chariot_inspector.battery.beans.Battery;
import fr_scapartois_auto.chariot_inspector.battery_usage.bean.BatteryUsage;
import fr_scapartois_auto.chariot_inspector.cart.beans.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BatteryUsageRepository extends JpaRepository<BatteryUsage, Long> {

    List<BatteryUsage> findByWorkSessionId(String workSessionId);

    List<BatteryUsage> findByCart(Cart cart);
}
