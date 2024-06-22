package fr_scapartois_auto.chariot_inspector.taurus_usage.mapper;

import fr_scapartois_auto.chariot_inspector.taurus_usage.bean.TaurusUsage;
import fr_scapartois_auto.chariot_inspector.taurus_usage.dto.TaurusUsageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaurusUsageMapper {

    TaurusUsageMapper INSTANCE = Mappers.getMapper(TaurusUsageMapper.class);

    @Mapping(source = "account.idAccount", target = "accountId")
    @Mapping(source = "taurus.idTaurus", target = "taurusId")
    TaurusUsageDTO fromTaurusUsage(TaurusUsage taurusUsage);

    @Mapping(source = "accountId", target = "account.idAccount")
    @Mapping(source = "taurusId", target = "taurus.idTaurus")
    TaurusUsage fromTaurusUsageDTO(TaurusUsageDTO taurusUsageDTO);
}
