package fr_scapartois_auto.chariot_inspector.audit.repositories;

import fr_scapartois_auto.chariot_inspector.audit.beans.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {
}
