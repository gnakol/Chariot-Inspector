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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

        CartDTO cartDTO = e.getCartDTO();
        if (cartDTO == null || cartDTO.getIdCart() == null) {
            throw new RuntimeException("Cart ID is missing");
        }

        Cart cart = this.cartRepository.findById(cartDTO.getIdCart())
                .orElseThrow(() -> new RuntimeException("id cart was not found"));

        e.setCartDTO(this.cartMapper.fromCart(cart));

        return this.batteryMapper.fromBattery(this.batteryRepository.save(this.batteryMapper.fromBatteryDTO(e)));
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

                    if (e.getCartDTO() != null && e.getCartDTO().getIdCart() != null) {
                        Cart cart = this.cartRepository.findById(e.getCartDTO().getIdCart())
                                .orElseThrow(() -> new RuntimeException("Cart with id : " + e.getCartDTO().getIdCart() + " was not found"));
                        battery.setCart(cart);
                    } else {
                        battery.setCart(null);
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
}
