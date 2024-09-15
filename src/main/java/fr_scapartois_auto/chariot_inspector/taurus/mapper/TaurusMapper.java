package fr_scapartois_auto.chariot_inspector.taurus.mapper;

import fr_scapartois_auto.chariot_inspector.taurus.bean.Taurus;
import fr_scapartois_auto.chariot_inspector.taurus.dto.TaurusDTO;
import fr_scapartois_auto.chariot_inspector.taurus_usage.mapper.TaurusUsageMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "default", uses = {TaurusUsageMapper.class})
public interface TaurusMapper {

    @Mapping(target = "taurusUsageDTOS", source = "taurusUsages")
    TaurusDTO fromTaurus(Taurus taurus);

    @Mapping(target = "taurusUsages", source = "taurusUsageDTOS")
    Taurus fromTaurusDTO(TaurusDTO taurusDTO);
}
