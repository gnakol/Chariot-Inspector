package fr_scapartois_auto.chariot_inspector.fuel_type.service;

import fr_scapartois_auto.chariot_inspector.fuel_type.bean.FuelType;
import fr_scapartois_auto.chariot_inspector.fuel_type.dto.FuelTypeDTO;
import fr_scapartois_auto.chariot_inspector.fuel_type.mapper.FuelTypeMapper;
import fr_scapartois_auto.chariot_inspector.fuel_type.mapper.FuelTypeMapperImpl;
import fr_scapartois_auto.chariot_inspector.fuel_type.repositorie.FuelTypeRepository;
import fr_scapartois_auto.chariot_inspector.webservices.Webservices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FuelTypeService implements Webservices<FuelTypeDTO> {

    private final FuelTypeRepository fuelTypeRepository;

    private final FuelTypeMapper fuelTypeMapper = new FuelTypeMapperImpl();

    @Override
    public Page<FuelTypeDTO> all(Pageable pageable) {
        return this.fuelTypeRepository.findAll(pageable)
                .map(this.fuelTypeMapper::fromFuelType);
    }

    @Override
    public FuelTypeDTO add(FuelTypeDTO e) {
        return this.fuelTypeMapper.fromFuelType(this.fuelTypeRepository.save(this.fuelTypeMapper.fromFuelTypeDTO(e)));
    }

    @Override
    public FuelTypeDTO update(Long id, FuelTypeDTO e) {
        return this.fuelTypeMapper.fromFuelType(this.fuelTypeRepository.findById(id)
                .map(fuelType -> {
                    if (e.getName() != null)
                        fuelType.setName(e.getName());

                    return this.fuelTypeRepository.save(fuelType);
                })
                .orElseThrow(() -> new RuntimeException("Fuel type with id : " +id+ " was not found")));
    }

    @Override
    public void remove(Long id) {

        Optional<FuelType> fuelType = this.fuelTypeRepository.findById(id);

        if (fuelType.isEmpty())
            throw new RuntimeException("this id fuel type was not found");

        this.fuelTypeRepository.delete(fuelType.get());

    }

    @Override
    public Optional<FuelTypeDTO> getById(Long id) {
        return this.fuelTypeRepository.findById(id)
                .map(this.fuelTypeMapper::fromFuelType);
    }
}
