package fr_scapartois_auto.chariot_inspector.cart.mappers;

import fr_scapartois_auto.chariot_inspector.cart.beans.Cart;
import fr_scapartois_auto.chariot_inspector.cart.dtos.CartDTO;
import fr_scapartois_auto.chariot_inspector.cart_category.mapper.CartCategoryMapper;
import fr_scapartois_auto.chariot_inspector.fuel_type.mapper.FuelTypeMapper;
import fr_scapartois_auto.chariot_inspector.manufacturer.mapper.ManufacturerMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "default", uses = {CartCategoryMapper.class, FuelTypeMapper.class, ManufacturerMapper.class})
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    @Mapping(target = "manufacturerId", source = "manufacturer.idManufacturer")
    @Mapping(target = "categoryId", source = "category.idCategory")
    @Mapping(target = "fuelTypeId", source = "fuelType.idFuelType")
    CartDTO fromCart(Cart cart);

    @Mapping(target = "manufacturer.idManufacturer", source = "manufacturerId")
    @Mapping(target = "category.idCategory", source = "categoryId")
    @Mapping(target = "fuelType.idFuelType", source = "fuelTypeId")
    Cart fromCartDTO(CartDTO cartDTO);
}
