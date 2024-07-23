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

import java.util.List;

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

    @DeleteMapping("remove-cart-by-id-range/{startId}/{endId}")
    public ResponseEntity<String> removeCartByIdRange(@Validated @PathVariable Long startId, @PathVariable Long endId)
    {
        this.cartService.removeCartByIdRange(startId, endId);

        return ResponseEntity.status(202).body("Cart id range was successfully remove");
    }

    @DeleteMapping("remove-cart-by-choose-id")
    public ResponseEntity<String> removeCartByChooseId(@Validated @RequestBody List<Long> ids)
    {
        this.cartService.removeCartByChooseId(ids);

        return ResponseEntity.status(202).body("Remove cart by choose id was successfully");
    }

    @GetMapping("get-cart-by-id/{idCart}")
    public ResponseEntity<CartDTO> getByIdCart(@Validated @PathVariable Long idCart)
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

    @GetMapping("/last-cart")
    public ResponseEntity<CartDTO> getLastCart() {
        CartDTO lastCart = cartService.getLastCart();
        return ResponseEntity.ok(lastCart);
    }

    @GetMapping("get-id-cart-by-number")
    public ResponseEntity<Long> getIdCartByNumber(@Validated @RequestParam String cartNumber)
    {
        Long idCart = this.cartService.getIdCartByNum(cartNumber);

        return ResponseEntity.ok(idCart);
    }
}
