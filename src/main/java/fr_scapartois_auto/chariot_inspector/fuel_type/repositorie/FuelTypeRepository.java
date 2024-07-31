package fr_scapartois_auto.chariot_inspector.fuel_type.repositorie;

import fr_scapartois_auto.chariot_inspector.fuel_type.bean.FuelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelTypeRepository extends JpaRepository<FuelType, Long> {
}
