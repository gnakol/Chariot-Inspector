package fr_scapartois_auto.chariot_inspector.permission.repositories;

import fr_scapartois_auto.chariot_inspector.permission.beans.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
