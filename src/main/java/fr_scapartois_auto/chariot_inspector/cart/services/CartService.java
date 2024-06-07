package fr_scapartois_auto.chariot_inspector.cart.services;

import fr_scapartois_auto.chariot_inspector.cart.dtos.CartDTO;
import fr_scapartois_auto.chariot_inspector.cart.mappers.CartMapper;
import fr_scapartois_auto.chariot_inspector.cart.mappers.CartMapperImpl;
import fr_scapartois_auto.chariot_inspector.cart.repositories.CartRepository;
import fr_scapartois_auto.chariot_inspector.webservices.Webservices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService implements Webservices<CartDTO> {

    private final CartRepository cartRepository;

    private final CartMapper cartMapper = new CartMapperImpl();


    @Override
    public Page<CartDTO> all(Pageable pageable) {
        return this.cartRepository.findAll(pageable)
                .map(this.cartMapper::fromCart);
    }

    @Override
    public CartDTO add(CartDTO e) {
        return null;
    }

    @Override
    public CartDTO update(Long id, CartDTO e) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Optional<CartDTO> getById(Long id) {
        return Optional.empty();
    }
}
