package fr_scapartois_auto.chariot_inspector.cart_category.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartCategoryDTO {

    private Long idCategory;

    private String name;
}
