package fr_scapartois_auto.chariot_inspector.uuid.repositories;

import fr_scapartois_auto.chariot_inspector.uuid.beans.Uuid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UuidRepository extends JpaRepository<Uuid, Long> {

    boolean existsByValueUuid(String uuid);
}
