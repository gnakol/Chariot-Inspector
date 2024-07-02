package fr_scapartois_auto.chariot_inspector.battery.services;

import fr_scapartois_auto.chariot_inspector.battery.beans.Battery;
import fr_scapartois_auto.chariot_inspector.battery.dtos.BatteryDTO;
import fr_scapartois_auto.chariot_inspector.battery.mappers.BatteryMapper;
import fr_scapartois_auto.chariot_inspector.battery.mappers.BatteryMapperImpl;
import fr_scapartois_auto.chariot_inspector.battery.repositories.BatteryRepository;
import fr_scapartois_auto.chariot_inspector.cart.beans.Cart;
import fr_scapartois_auto.chariot_inspector.cart.dtos.CartDTO;
import fr_scapartois_auto.chariot_inspector.cart.mappers.CartMapper;
import fr_scapartois_auto.chariot_inspector.cart.mappers.CartMapperImpl;
import fr_scapartois_auto.chariot_inspector.cart.repositories.CartRepository;
import fr_scapartois_auto.chariot_inspector.uuid.services.UuidService;
import fr_scapartois_auto.chariot_inspector.webservices.Webservices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BatteryService implements Webservices<BatteryDTO> {

    private final BatteryRepository batteryRepository;

    private final BatteryMapper batteryMapper = new BatteryMapperImpl();

    private final UuidService uuidService;

    private final CartMapper cartMapper = new CartMapperImpl();

    private final CartRepository cartRepository;

    @Override
    public Page<BatteryDTO> all(Pageable pageable) {
        return this.batteryRepository.findAll(pageable)
                .map(this.batteryMapper::fromBattery);
    }

    @Override
    public BatteryDTO add(BatteryDTO e) {

        e.setRefBattery(this.uuidService.generateUuid());

        Battery battery = this.batteryMapper.fromBatteryDTO(e);

        Optional<Cart> cart = this.cartRepository.findById(battery.getCart().getIdCart());

        if (cart.isPresent())
        {
            battery.setCart(cart.get());

            Battery savedBattery = this.batteryRepository.save(battery);

            return this.batteryMapper.fromBattery(savedBattery);
        }
        else
            throw new RuntimeException("Cart not found");
    }


    @Override
    public BatteryDTO update(Long id, BatteryDTO e) {
        return this.batteryMapper.fromBattery(this.batteryRepository.findById(id)
                .map(battery -> {
                    if (e.getBatteryNumber() != null)
                        battery.setBatteryNumber(e.getBatteryNumber());
                    if (e.getChargeLevel() != null)
                        battery.setChargeLevel(e.getChargeLevel());
                    if (e.getState() != null)
                        battery.setState(e.getState());

                    if (e.getIdCart() != null)
                    {
                        Optional<Cart> cart = this.cartRepository.findById(e.getIdCart());
                        battery.setCart(cart.get());
                    }

                    return this.batteryRepository.save(battery);
                })
                .orElseThrow(() -> new RuntimeException("Battery with id : " + id + " was not found")));
    }


    @Override
    public void remove(Long id) {

        Optional<Battery> battery = this.batteryRepository.findById(id);

        if (battery.isEmpty())
        {
            throw new RuntimeException("battery not found");
        }

        this.batteryRepository.delete(battery.get());

    }

    @Override
    public Optional<BatteryDTO> getById(Long id) {
        return this.batteryRepository.findById(id)
                .map(this.batteryMapper::fromBattery);
    }

    public Long getIdBatteryByNum(Long batteryNumber)
    {
        Optional<Battery> battery = this.batteryRepository.findByBatteryNumber(batteryNumber);

        if (battery.isEmpty())
            throw new RuntimeException("number of battery with : "+batteryNumber+ " was not found");

        return battery.get().getIdBattery();
    }

    public Page<BatteryDTO> allBatteryByCart(Long idCart, Pageable pageable)
    {
        Optional<Cart> cart = this.cartRepository.findById(idCart);

        if (cart.isEmpty())
            throw new RuntimeException("Cart with id : " +idCart+ " was not found");

        List<Battery> batteryList = this.batteryRepository.findBatteryByCart(cart.get());
        List<BatteryDTO> batteryDTOS = batteryList.stream().map(this.batteryMapper::fromBattery).collect(Collectors.toList());

        int start = Math.min((int) pageable.getOffset(), batteryDTOS.size());
        int end = Math.min((start + pageable.getPageSize()), batteryDTOS.size());

        return new PageImpl<>(batteryDTOS.subList(start, end), pageable, batteryDTOS.size());
    }
}
