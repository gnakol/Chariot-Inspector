package fr_scapartois_auto.chariot_inspector.role.mappers;

import fr_scapartois_auto.chariot_inspector.role.beans.Role;
import fr_scapartois_auto.chariot_inspector.role.dtos.RoleDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "default")
public interface RoleMapper {

    RoleDTO fromRole(Role role);

    Role fromRoleDTO(RoleDTO roleDTO);
}
