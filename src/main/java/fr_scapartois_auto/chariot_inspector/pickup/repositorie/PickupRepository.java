package fr_scapartois_auto.chariot_inspector.pickup.repositorie;

import fr_scapartois_auto.chariot_inspector.pickup.bean.Pickup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PickupRepository extends JpaRepository<Pickup, Long> {
}
