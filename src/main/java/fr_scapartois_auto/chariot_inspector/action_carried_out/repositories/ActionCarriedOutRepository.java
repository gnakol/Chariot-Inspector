package fr_scapartois_auto.chariot_inspector.action_carried_out.repositories;

import fr_scapartois_auto.chariot_inspector.action_carried_out.beans.ActionCarriedOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionCarriedOutRepository extends JpaRepository<ActionCarriedOut, Long> {
}
