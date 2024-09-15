package fr_scapartois_auto.chariot_inspector.taurus.repositorie;

import fr_scapartois_auto.chariot_inspector.taurus.bean.Taurus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaurusRepository extends JpaRepository<Taurus, Long> {

    Optional<Taurus> findByTaurusNumber(Long numberTaurus);
}
