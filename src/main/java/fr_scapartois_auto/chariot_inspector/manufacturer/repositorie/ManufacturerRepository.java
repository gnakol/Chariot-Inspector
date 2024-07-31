package fr_scapartois_auto.chariot_inspector.manufacturer.repositorie;

import fr_scapartois_auto.chariot_inspector.manufacturer.bean.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
}
