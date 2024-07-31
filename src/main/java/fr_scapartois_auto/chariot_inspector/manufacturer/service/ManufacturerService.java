package fr_scapartois_auto.chariot_inspector.manufacturer.service;

import fr_scapartois_auto.chariot_inspector.manufacturer.bean.Manufacturer;
import fr_scapartois_auto.chariot_inspector.manufacturer.dto.ManufacturerDTO;
import fr_scapartois_auto.chariot_inspector.manufacturer.mapper.ManufacturerMapper;
import fr_scapartois_auto.chariot_inspector.manufacturer.mapper.ManufacturerMapperImpl;
import fr_scapartois_auto.chariot_inspector.manufacturer.repositorie.ManufacturerRepository;
import fr_scapartois_auto.chariot_inspector.webservices.Webservices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManufacturerService implements Webservices<ManufacturerDTO> {

    private final ManufacturerRepository manufacturerRepository;

    private final ManufacturerMapper manufacturerMapper = new ManufacturerMapperImpl();


    @Override
    public Page<ManufacturerDTO> all(Pageable pageable) {
        return this.manufacturerRepository.findAll(pageable)
                .map(this.manufacturerMapper::fromManufacturer);
    }

    @Override
    public ManufacturerDTO add(ManufacturerDTO e) {
        return this.manufacturerMapper.fromManufacturer(this.manufacturerRepository.save(this.manufacturerMapper.fromManufacturerDTO(e)));
    }

    @Override
    public ManufacturerDTO update(Long id, ManufacturerDTO e) {
        return this.manufacturerMapper.fromManufacturer(this.manufacturerRepository.findById(id)
                .map(manufacturer -> {
                    if (e.getName() != null)
                        manufacturer.setName(e.getName());

                    return this.manufacturerRepository.save(manufacturer);
                })
                .orElseThrow(() -> new RuntimeException("Manufacturer with id : " +id+ " was not found")));
    }

    @Override
    public void remove(Long id) {

        Optional<Manufacturer> manufacturer = this.manufacturerRepository.findById(id);

        if (manufacturer.isEmpty())
            throw new RuntimeException("Sorry id manufacturer : " +id+ " was not found");

        this.manufacturerRepository.delete(manufacturer.get());

    }

    @Override
    public Optional<ManufacturerDTO> getById(Long id) {
        return this.manufacturerRepository.findById(id)
                .map(this.manufacturerMapper::fromManufacturer);
    }
}
