package fr_scapartois_auto.chariot_inspector.cart_category.service;

import fr_scapartois_auto.chariot_inspector.cart_category.bean.CartCategory;
import fr_scapartois_auto.chariot_inspector.cart_category.dto.CartCategoryDTO;
import fr_scapartois_auto.chariot_inspector.cart_category.mapper.CartCategoryMapper;
import fr_scapartois_auto.chariot_inspector.cart_category.mapper.CartCategoryMapperImpl;
import fr_scapartois_auto.chariot_inspector.cart_category.repositorie.CartCategoryRepository;
import fr_scapartois_auto.chariot_inspector.webservices.Webservices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartCategoryService implements Webservices<CartCategoryDTO> {

    private final CartCategoryRepository cartCategoryRepository;

    private final CartCategoryMapper cartCategoryMapper = new CartCategoryMapperImpl();
    @Override
    public Page<CartCategoryDTO> all(Pageable pageable) {
        return this.cartCategoryRepository.findAll(pageable)
                .map(this.cartCategoryMapper::fromCartCategory);
    }

    @Override
    public CartCategoryDTO add(CartCategoryDTO e) {
        return this.cartCategoryMapper.fromCartCategory(this.cartCategoryRepository.save(this.cartCategoryMapper.fromCartCategoryDTO(e)));
    }

    @Override
    public CartCategoryDTO update(Long id, CartCategoryDTO e) {
        return this.cartCategoryMapper.fromCartCategory(this.cartCategoryRepository.findById(id)
                .map(cartCategory -> {
                    if (e.getName() != null)
                        cartCategory.setName(e.getName());
                    return this.cartCategoryRepository.save(cartCategory);
                })
                .orElseThrow(() -> new RuntimeException("this id cart category was not found")));
    }

    @Override
    public void remove(Long id) {

        Optional<CartCategory> cartCategory = this.cartCategoryRepository.findById(id);

        if (cartCategory.isEmpty())
            throw new RuntimeException("sorry this id was not found");

        this.cartCategoryRepository.delete(cartCategory.get());

    }

    @Override
    public Optional<CartCategoryDTO> getById(Long id) {
        return this.cartCategoryRepository.findById(id)
                .map(this.cartCategoryMapper::fromCartCategory);
    }
}
