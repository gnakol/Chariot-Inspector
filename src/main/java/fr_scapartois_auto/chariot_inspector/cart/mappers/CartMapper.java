package fr_scapartois_auto.chariot_inspector.cart.mappers;

import fr_scapartois_auto.chariot_inspector.cart.beans.Cart;
import fr_scapartois_auto.chariot_inspector.cart.dtos.CartDTO;
import fr_scapartois_auto.chariot_inspector.audit.mappers.AuditMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "default")
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    CartDTO fromCart(Cart cart);

    Cart fromCartDTO(CartDTO cartDTO);
}
