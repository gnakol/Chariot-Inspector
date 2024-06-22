package fr_scapartois_auto.chariot_inspector.pickup.mapper;

import fr_scapartois_auto.chariot_inspector.pickup.bean.Pickup;
import fr_scapartois_auto.chariot_inspector.pickup.dto.PickupDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PickupMapper {

    PickupMapper INSTANCE = Mappers.getMapper(PickupMapper.class);

    @Mapping(source = "account.idAccount", target = "accountId")
    @Mapping(source = "cart.idCart", target = "cartId")
    PickupDTO fromPickup(Pickup pickup);

    @Mapping(source = "accountId", target = "account.idAccount")
    @Mapping(source = "cartId", target = "cart.idCart")
    Pickup fromPickupDTO(PickupDTO pickupDTO);
}
