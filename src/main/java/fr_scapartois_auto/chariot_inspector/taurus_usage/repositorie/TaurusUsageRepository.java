package fr_scapartois_auto.chariot_inspector.taurus_usage.repositorie;

import fr_scapartois_auto.chariot_inspector.taurus_usage.bean.TaurusUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaurusUsageRepository extends JpaRepository<TaurusUsage, Long> {
}
