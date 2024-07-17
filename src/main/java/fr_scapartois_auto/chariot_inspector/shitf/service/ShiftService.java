package fr_scapartois_auto.chariot_inspector.shitf.service;

import fr_scapartois_auto.chariot_inspector.shitf.bean.Shift;
import fr_scapartois_auto.chariot_inspector.shitf.dto.ShiftDTO;
import fr_scapartois_auto.chariot_inspector.shitf.mapper.ShiftMapper;
import fr_scapartois_auto.chariot_inspector.shitf.mapper.ShiftMapperImpl;
import fr_scapartois_auto.chariot_inspector.shitf.repository.ShiftRepository;
import fr_scapartois_auto.chariot_inspector.webservices.Webservices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShiftService implements Webservices<ShiftDTO> {

    private final ShiftRepository shiftRepository;

    private final ShiftMapper shiftMapper = new ShiftMapperImpl();

    @Override
    public Page<ShiftDTO> all(Pageable pageable) {
        return this.shiftRepository.findAll(pageable)
                .map(this.shiftMapper::fromShift);
    }

    @Override
    public ShiftDTO add(ShiftDTO e) {
        return this.shiftMapper.fromShift(this.shiftRepository.save(this.shiftMapper.fromShiftDTO(e)));
    }

    @Override
    public ShiftDTO update(Long id, ShiftDTO e) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Optional<ShiftDTO> getById(Long id) {
        return this.shiftRepository.findById(id)
                .map(this.shiftMapper::fromShift);
    }

    public ShiftDTO determineShift(LocalTime currentTime) {
        List<Shift> shifts = this.shiftRepository.findAll();

        for (Shift shift : shifts) {
            if (isWithinShift(currentTime, shift)) {
                return this.shiftMapper.fromShift(shift);
            }
        }
        throw new RuntimeException("No shift found for the current time");
    }

    private boolean isWithinShift(LocalTime currentTime, Shift shift) {
        if (shift.getEndTime().isBefore(shift.getStartTime())) {
            // This shift wraps around midnight
            return !currentTime.isBefore(shift.getStartTime()) || !currentTime.isAfter(shift.getEndTime());
        } else {
            // This shift does not wrap around midnight
            return !currentTime.isBefore(shift.getStartTime()) && !currentTime.isAfter(shift.getEndTime());
        }
    }
}
