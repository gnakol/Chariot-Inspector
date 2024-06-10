package fr_scapartois_auto.chariot_inspector.cart.controller;

import fr_scapartois_auto.chariot_inspector.cart.dtos.CartDTO;
import fr_scapartois_auto.chariot_inspector.cart.services.CartService;
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
@RequestMapping("cart")
@Slf4j
public class CartController {

    private final CartService cartService;

    @GetMapping("all-cart")
    public ResponseEntity<Page<CartDTO>> allPage(Pageable pageable)
    {
        return  ResponseEntity.ok(this.cartService.all(pageable));
    }

    @PostMapping("add-new-cart")
    public ResponseEntity<CartDTO> addNewCart(@Validated @RequestBody CartDTO cartDTO)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.cartService.add(cartDTO));
    }

    @PutMapping("update-cart/{idCart}")
    public ResponseEntity<CartDTO> updateCart(@Validated @PathVariable Long idCart, @RequestBody CartDTO cartDTO)
    {
        return ResponseEntity.status(202).body(this.cartService.update(idCart, cartDTO));
    }

    @DeleteMapping("remove-cart/{idCart}")
    public ResponseEntity<String> removeCart(@Validated @PathVariable Long idCart)
    {
        this.cartService.remove(idCart);

        return ResponseEntity.status(202).body("Cart with id :" +idCart+ "was successfully remove");
    }

    @GetMapping("get-cart-by-id/{idCart}")
    public ResponseEntity<CartDTO> getBYIdCart(@Validated @PathVariable Long idCart)
    {
        return this.cartService.getById(idCart)
                .map(cartDTO -> {
                    log.info("cart with id :" +idCart+ "was found");
                    return new  ResponseEntity<>(cartDTO, HttpStatus.OK);
                })
                .orElseThrow(() ->{
                    log.error("cart with id : " +idCart+ " was not found");
                    return new RuntimeException("sorry this id was not found");
                });
    }






}
