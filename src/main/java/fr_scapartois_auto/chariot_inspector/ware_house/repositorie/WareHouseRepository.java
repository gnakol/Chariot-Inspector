package fr_scapartois_auto.chariot_inspector.ware_house.repositorie;

import fr_scapartois_auto.chariot_inspector.ware_house.bean.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WareHouseRepository extends JpaRepository<WareHouse, Long> {
}
