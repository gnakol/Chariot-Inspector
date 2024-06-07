package fr_scapartois_auto.chariot_inspector.role.repositories;

import fr_scapartois_auto.chariot_inspector.role.beans.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(String role);

}
