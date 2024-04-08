package ru.task.iss.cart.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.task.iss.cart.service.CartService;
import ru.task.iss.cart.service.dtos.CartItemDto;
import ru.task.iss.models.CartItem;

import java.util.Collection;

@RestController
@RequestMapping(path = "/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;
    @PostMapping
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    public CartItem addItemToCart(
            @RequestBody CartItem cartItem
    ) {
        log.info("Запрос POST на добавление товара /cart");
        return cartService.addItemToCart(cartItem);
    }

    @GetMapping
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    public Collection<CartItem> getItemsFromCart(
    ) {
        log.info("Запрос GET на получение товаров из корзины /cart");
        return cartService.getItemsFromCart();
    }
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @DeleteMapping("/{vendorCode}")
    public void removeItemFromCart(
            @PathVariable Long vendorCode
    ) {
        log.info("Запрос DELETE на удаление товара /cart/{}",vendorCode);
        cartService.removeItemFromCart(vendorCode);
    }
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @DeleteMapping
    public void cleanCart(
    ) {
        log.info("Запрос DELETE удаление товаров из корзины /cart");
        cartService.cleanCart();
    }
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("/buy")
    public void buyCart(
    ) {
        log.info("Запрос POST купить корзину /cart/buy");
        cartService.buyCart();
    }
}
