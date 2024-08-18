package fr_scapartois_auto.chariot_inspector.cart.services;

import fr_scapartois_auto.chariot_inspector.cart.beans.Cart;
import fr_scapartois_auto.chariot_inspector.cart.dtos.CartDTO;
import fr_scapartois_auto.chariot_inspector.cart.mappers.CartMapper;
import fr_scapartois_auto.chariot_inspector.cart.mappers.CartMapperImpl;
import fr_scapartois_auto.chariot_inspector.cart.repositories.CartRepository;
import fr_scapartois_auto.chariot_inspector.cart_category.bean.CartCategory;
import fr_scapartois_auto.chariot_inspector.cart_category.repositorie.CartCategoryRepository;
import fr_scapartois_auto.chariot_inspector.exception.other.ResourceNotFoundException;
import fr_scapartois_auto.chariot_inspector.fuel_type.bean.FuelType;
import fr_scapartois_auto.chariot_inspector.fuel_type.repositorie.FuelTypeRepository;
import fr_scapartois_auto.chariot_inspector.manufacturer.bean.Manufacturer;
import fr_scapartois_auto.chariot_inspector.manufacturer.repositorie.ManufacturerRepository;
import fr_scapartois_auto.chariot_inspector.webservices.Webservices;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CartService implements Webservices<CartDTO> {

    private final CartRepository cartRepository;

    private final CartMapper cartMapper = new CartMapperImpl();

    private final FuelTypeRepository fuelTypeRepository;

    private final ManufacturerRepository manufacturerRepository;

    private final CartCategoryRepository cartCategoryRepository;


    @Override
    public Page<CartDTO> all(Pageable pageable) {
        return this.cartRepository.findAll(pageable)
                .map(this.cartMapper::fromCart);
    }

    @Override
    public CartDTO add(CartDTO e) {

        Cart cart = this.cartMapper.fromCartDTO(e);

        Optional<Manufacturer> manufacturer = this.manufacturerRepository.findById(cart.getManufacturer().getIdManufacturer());
        Optional<CartCategory> cartCategory = this.cartCategoryRepository.findById(cart.getCategory().getIdCategory());
        Optional<FuelType> fuelType = this.fuelTypeRepository.findById(cart.getFuelType().getIdFuelType());

        if (manufacturer.isPresent() && cartCategory.isPresent() && fuelType.isPresent())
        {
            cart.setManufacturer(manufacturer.get());
            cart.setCategory(cartCategory.get());
            cart.setFuelType(fuelType.get());

            Cart savedCart = this.cartRepository.save(cart);

            return this.cartMapper.fromCart(savedCart);
        }
        else
            throw new RuntimeException("Sorry Fuel-type and Cart-category and Manufacturer was not found");
    }

    @Override
    public CartDTO update(Long id, CartDTO e) {

        Cart cartt = this.cartMapper.fromCartDTO(e);

        return this.cartMapper.fromCart(this.cartRepository.findById(id)
                .map(cart -> {
                    if (e.getCartNumber() != null)
                        cart.setCartNumber(e.getCartNumber());

                    if (e.getManufacturerId() != null)
                    {
                        Optional<Manufacturer> manufacturer = this.manufacturerRepository.findById(cartt.getManufacturer().getIdManufacturer());
                        cart.setManufacturer(manufacturer.get());

                    }

                    if (e.getCategoryId() != null)
                    {
                        Optional<CartCategory> cartCategory = this.cartCategoryRepository.findById(cartt.getCategory().getIdCategory());
                        cart.setCategory(cartCategory.get());
                    }

                    if (e.getFuelTypeId() != null)
                    {
                        Optional<FuelType> fuelType = this.fuelTypeRepository.findById(cartt.getFuelType().getIdFuelType());
                        cart.setFuelType(fuelType.get());
                    }

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
