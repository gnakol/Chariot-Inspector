package fr_scapartois_auto.chariot_inspector.cart_category.mapper;

import fr_scapartois_auto.chariot_inspector.cart_category.bean.CartCategory;
import fr_scapartois_auto.chariot_inspector.cart_category.dto.CartCategoryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "default")
public interface CartCategoryMapper {

    CartCategoryDTO fromCartCategory(CartCategory cartCategory);

    CartCategory fromCartCategoryDTO(CartCategoryDTO cartCategoryDTO);
}
