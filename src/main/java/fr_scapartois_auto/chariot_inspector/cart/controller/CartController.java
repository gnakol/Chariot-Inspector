package fr_scapartois_auto.chariot_inspector.cart.controller;

import fr_scapartois_auto.chariot_inspector.cart.dtos.CartDTO;
import fr_scapartois_auto.chariot_inspector.cart.services.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
