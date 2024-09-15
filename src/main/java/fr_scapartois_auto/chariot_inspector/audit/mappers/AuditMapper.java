package fr_scapartois_auto.chariot_inspector.audit.mappers;

import fr_scapartois_auto.chariot_inspector.account.mappers.AccountMapper;
import fr_scapartois_auto.chariot_inspector.audit.beans.Audit;
import fr_scapartois_auto.chariot_inspector.audit.dtos.AuditDTO;
import fr_scapartois_auto.chariot_inspector.cart.mappers.CartMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "default", uses = {CartMapper.class, AccountMapper.class})
public interface AuditMapper {

    @Mapping(target = "accountId", source = "account.idAccount")
    @Mapping(target = "cartId", source = "cart.idCart")
    AuditDTO fromAudit(Audit audit);

    @Mapping(target = "account.idAccount", source = "accountId")
    @Mapping(target = "cart.idCart", source = "cartId")
    Audit fromAuditDTO(AuditDTO auditDTO);
}
