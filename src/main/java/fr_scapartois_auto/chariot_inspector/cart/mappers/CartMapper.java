package fr_scapartois_auto.chariot_inspector.cart.mappers;

import fr_scapartois_auto.chariot_inspector.cart.beans.Cart;
import fr_scapartois_auto.chariot_inspector.cart.dtos.CartDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "default")
public interface CartMapper {

    CartDTO fromCart(Cart cart);

    Cart fromCartDTO(CartDTO cartDTO);
}
