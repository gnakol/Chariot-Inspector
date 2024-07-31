package fr_scapartois_auto.chariot_inspector.ware_house.service;

import fr_scapartois_auto.chariot_inspector.ware_house.bean.WareHouse;
import fr_scapartois_auto.chariot_inspector.ware_house.dto.WareHouseDTO;
import fr_scapartois_auto.chariot_inspector.ware_house.mapper.WareHouseMapper;
import fr_scapartois_auto.chariot_inspector.ware_house.mapper.WareHouseMapperImpl;
import fr_scapartois_auto.chariot_inspector.ware_house.repositorie.WareHouseRepository;
import fr_scapartois_auto.chariot_inspector.webservices.Webservices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WareHouseService implements Webservices<WareHouseDTO> {

    private final WareHouseRepository wareHouseRepository;

    private final WareHouseMapper wareHouseMapper = new WareHouseMapperImpl();


    @Override
    public Page<WareHouseDTO> all(Pageable pageable) {
        return this.wareHouseRepository.findAll(pageable)
                .map(this.wareHouseMapper::fromWareHouse);
    }

    @Override
    public WareHouseDTO add(WareHouseDTO e) {
        return this.wareHouseMapper.fromWareHouse(this.wareHouseRepository.save(this.wareHouseMapper.fromWareHouseDTO(e)));
    }

    @Override
    public WareHouseDTO update(Long id, WareHouseDTO e) {
        return this.wareHouseMapper.fromWareHouse(this.wareHouseRepository.findById(id)
                .map(wareHouse -> {
                    if (e.getName() != null)
                        wareHouse.setName(e.getName());

                    return this.wareHouseRepository.save(wareHouse);
                })
                .orElseThrow(() -> new RuntimeException("Ware house with id : " +id+ " was nof found")));
    }

    @Override
    public void remove(Long id) {

        Optional<WareHouse> wareHouse = this.wareHouseRepository.findById(id);

        if (wareHouse.isEmpty())
            throw new RuntimeException("not found id ware house");

        this.wareHouseRepository.delete(wareHouse.get());

    }

    @Override
    public Optional<WareHouseDTO> getById(Long id) {
        return this.wareHouseRepository.findById(id)
                .map(this.wareHouseMapper::fromWareHouse);
    }
}
