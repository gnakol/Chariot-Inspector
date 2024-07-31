package fr_scapartois_auto.chariot_inspector.cart_category.controller;

import fr_scapartois_auto.chariot_inspector.cart_category.bean.CartCategory;
import fr_scapartois_auto.chariot_inspector.cart_category.dto.CartCategoryDTO;
import fr_scapartois_auto.chariot_inspector.cart_category.service.CartCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("cart-category")
public class CartCategoryController {

    private final CartCategoryService cartCategoryService;

    @GetMapping("all-cart-category")
    public ResponseEntity<Page<CartCategoryDTO>> allCartCategory(Pageable pageable)
    {
        return ResponseEntity.ok(this.cartCategoryService.all(pageable));
    }

    @PostMapping("add-new-cart-category")
    public ResponseEntity<CartCategoryDTO> addNewCartCategory(@Validated @RequestBody CartCategoryDTO cartCategoryDTO)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.cartCategoryService.add(cartCategoryDTO));
    }

    @PutMapping("update-cart-category/{idCartCategory}")
    public ResponseEntity<CartCategoryDTO> updateCartCategory(@Validated @PathVariable Long idCartCategory, @RequestBody CartCategoryDTO cartCategory)
    {
        return ResponseEntity.status(202).body(this.cartCategoryService.update(idCartCategory, cartCategory));
    }

    @DeleteMapping("remove-cart-category/{idCartCategory}")
    public ResponseEntity<String> removeCartCategory(@Validated @PathVariable Long idCartCategory)
    {
        this.cartCategoryService.remove(idCartCategory);

        return ResponseEntity.status(202).body("Cart category was successfully remove");
    }

    @GetMapping("get-cart-category-by-id/{idCartCategory}")
    public ResponseEntity<CartCategoryDTO> getByIdCartCategory(@Validated @PathVariable Long idCartCategory)
    {
        return this.cartCategoryService.getById(idCartCategory)
                .map(cartCategoryDTO -> {
                    log.info("Cart category with id : " +idCartCategory+ " was found");
                    return new ResponseEntity<>(cartCategoryDTO, HttpStatus.OK);
                })
                .orElseThrow(() -> {
                    log.error("Cart category with id : " +idCartCategory+ " was not found");
                    throw new RuntimeException(" sorry this id cart category was not found");
                });
    }
}
