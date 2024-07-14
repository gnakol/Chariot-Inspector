package fr_scapartois_auto.chariot_inspector.shitf.mapper;

import fr_scapartois_auto.chariot_inspector.shitf.bean.Shift;
import fr_scapartois_auto.chariot_inspector.shitf.dto.ShiftDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "default")
public interface ShiftMapper {

    ShiftDTO fromShift(Shift shift);

    Shift fromShiftDTO(ShiftDTO shiftDTO);
}
