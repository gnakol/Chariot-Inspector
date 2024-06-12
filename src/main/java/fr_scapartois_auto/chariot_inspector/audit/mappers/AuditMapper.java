package fr_scapartois_auto.chariot_inspector.audit.mappers;

import fr_scapartois_auto.chariot_inspector.audit.beans.Audit;
import fr_scapartois_auto.chariot_inspector.audit.dtos.AuditDTO;
import fr_scapartois_auto.chariot_inspector.cart.mappers.CartMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "default", uses = {CartMapper.class})
public interface AuditMapper {
    AuditMapper INSTANCE = Mappers.getMapper(AuditMapper.class);

    @Mappings({
            @Mapping(target = "accompaniedDTOS", source = "accompanieds")  // Adjust this mapping if needed
    })
    AuditDTO fromAudit(Audit audit);

    @Mappings({
            @Mapping(target = "accompanieds", source = "accompaniedDTOS")  // Adjust this mapping if needed
    })
    Audit fromAuditDTO(AuditDTO auditDTO);
}
