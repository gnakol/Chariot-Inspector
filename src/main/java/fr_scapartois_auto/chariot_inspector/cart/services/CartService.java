package fr_scapartois_auto.chariot_inspector.cart.services;

import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import fr_scapartois_auto.chariot_inspector.account.mappers.AccountMapper;
import fr_scapartois_auto.chariot_inspector.account.mappers.AccountMapperImpl;
import fr_scapartois_auto.chariot_inspector.cart.beans.Cart;
import fr_scapartois_auto.chariot_inspector.cart.dtos.CartDTO;
import fr_scapartois_auto.chariot_inspector.cart.mappers.CartMapper;
import fr_scapartois_auto.chariot_inspector.cart.mappers.CartMapperImpl;
import fr_scapartois_auto.chariot_inspector.cart.repositories.CartRepository;
import fr_scapartois_auto.chariot_inspector.uuid.services.UuidService;
import fr_scapartois_auto.chariot_inspector.webservices.Webservices;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService implements Webservices<CartDTO> {

    private final CartRepository cartRepository;

    private final CartMapper cartMapper = new CartMapperImpl();

    private final UuidService uuidService;

    private final AccountMapper accountMapper = new AccountMapperImpl();


    @Override
    public Page<CartDTO> all(Pageable pageable) {
        return this.cartRepository.findAll(pageable)
                .map(this.cartMapper::fromCart);
    }

    @Override
    public CartDTO add(CartDTO e) {

        return this.cartMapper.fromCart(this.cartRepository.save(this.cartMapper.fromCartDTO(e)));
    }

    @Override
    public CartDTO update(Long id, CartDTO e) {
        return this.cartMapper.fromCart(this.cartRepository.findById(id)
                .map(cart -> {
                    if (e.getCartNumber() != null)
                        cart.setCartNumber(e.getCartNumber());

                    if (e.getBrand() != null)
                        cart.setBrand(e.getBrand());

                    return this.cartRepository.save(cart);
                })
                .orElseThrow(() -> new RuntimeException("cart with id " +id+ " was not found")));
    }

    @Override
    public void remove(Long id) {

        Optional<Cart> cart = this.cartRepository.findById(id);

        if (cart.isEmpty())
        {
            throw  new RuntimeException("cart with id " +id+ " was not found");
        }

        this.cartRepository.delete(cart.get());

    }

    @Transactional
    public void removeCartByIdRange(Long startId, Long endId)
    {
        this.cartRepository.deleteByIdRange(startId, endId);
    }

    @Transactional
    public void removeCartByChooseId(List<Long> ids)
    {
        this.cartRepository.deleteByIds(ids);
    }

    @Override
    public Optional<CartDTO> getById(Long id) {
        return this.cartRepository.findById(id)
                .map(this.cartMapper::fromCart);
    }

    public CartDTO getLastCart() {
        Cart lastCart = cartRepository.findTopByOrderByIdCartDesc(); // Assurez-vous que cette méthode existe dans le repository
        return cartMapper.fromCart(lastCart);
    }

    public Long getIdCartByNum(String cartNumber)
    {
        Optional<Cart> cart = this.cartRepository.findByCartNumber(cartNumber);

        if (cart.isEmpty())
            throw new RuntimeException("Cart loss was not found");

        return cart.get().getIdCart();
    }

}
