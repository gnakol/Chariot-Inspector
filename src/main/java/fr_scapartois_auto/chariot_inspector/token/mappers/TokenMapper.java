package fr_scapartois_auto.chariot_inspector.token.mappers;

import fr_scapartois_auto.chariot_inspector.token.beans.Token;
import fr_scapartois_auto.chariot_inspector.token.dtos.TokenDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "default")
public interface TokenMapper {

    @Mapping(target = "accountId", source = "account.idAccount")
    TokenDTO fromToken(Token token);

    @Mapping(target = "account.idAccount", source = "accountId")
    Token fromTokenDTO(TokenDTO tokenDTO);
}
