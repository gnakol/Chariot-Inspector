package fr_scapartois_auto.chariot_inspector.taurus_usage.mapper;

import fr_scapartois_auto.chariot_inspector.accompanied.beans.taurus.mapper.TaurusMapper;
import fr_scapartois_auto.chariot_inspector.account.mappers.AccountMapper;
import fr_scapartois_auto.chariot_inspector.taurus_usage.bean.TaurusUsage;
import fr_scapartois_auto.chariot_inspector.taurus_usage.dto.TaurusUsageDTO;
import fr_scapartois_auto.chariot_inspector.taurus_usage.dto.TaurusUsageDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "default", uses = {AccountMapper.class, TaurusMapper.class})
public interface TaurusUsageMapper {

    TaurusUsageMapper INSTANCE = Mappers.getMapper(TaurusUsageMapper.class);

    @Mapping(source = "account.idAccount", target = "accountId")
    @Mapping(source = "taurus.idTaurus", target = "taurusId")
    TaurusUsageDTO fromTaurusUsage(TaurusUsage taurusUsage);

    @Mapping(source = "accountId", target = "account.idAccount")
    @Mapping(source = "taurusId", target = "taurus.idTaurus")
    TaurusUsage fromTaurusUsageDTO(TaurusUsageDTO taurusUsageDTO);

/*    @Mapping(target = "accountDTO", source = "account")
    @Mapping(target = "taurusDTO", source = "taurus")
    TaurusUsageDetailDTO fromTaurusUsageO(TaurusUsage taurusUsage);

    @Mapping(target = "account", source = "accountDTO")
    @Mapping(target = "taurus", source = "taurusDTO")
    TaurusUsage fromTaurusUsageDTOO(TaurusUsageDTO taurusUsageDTO);*/
}
