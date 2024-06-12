package fr_scapartois_auto.chariot_inspector.cart.mappers;

import fr_scapartois_auto.chariot_inspector.cart.beans.Cart;
import fr_scapartois_auto.chariot_inspector.cart.dtos.CartDTO;
import fr_scapartois_auto.chariot_inspector.audit.mappers.AuditMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "default", uses = {AuditMapper.class})
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    @Mappings({
            @Mapping(target = "accompaniedDTOS", source = "accompanieds")  // Adjust this mapping if needed
    })
    CartDTO fromCart(Cart cart);

    @Mappings({
            @Mapping(target = "accompanieds", source = "accompaniedDTOS")  // Adjust this mapping if needed
    })
    Cart fromCartDTO(CartDTO cartDTO);
}
