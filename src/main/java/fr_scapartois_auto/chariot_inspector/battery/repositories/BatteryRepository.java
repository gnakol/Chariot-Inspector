package fr_scapartois_auto.chariot_inspector.battery.repositories;

import fr_scapartois_auto.chariot_inspector.battery.beans.Battery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BatteryRepository extends JpaRepository<Battery, Long> {

    Optional<Battery> findByBatteryNumber(Long batteryNumber);

}
