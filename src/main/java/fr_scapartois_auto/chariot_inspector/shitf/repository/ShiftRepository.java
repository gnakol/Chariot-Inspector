package fr_scapartois_auto.chariot_inspector.shitf.repository;

import fr_scapartois_auto.chariot_inspector.shitf.bean.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {
}
