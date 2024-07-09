package ru.task.iss.cart.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.task.iss.cart.service.CartService;
import ru.task.iss.items.services.dtos.ItemDto;
import ru.task.iss.models.CartItem;

import java.util.Collection;

@RestController
@RequestMapping(path = "/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ItemDto addItemToCart(
            @RequestBody CartItem cartItem
    ) {
        log.info("Запрос POST на добавление товара /cart");
        return cartService.addItemToCart(cartItem);
    }

    @GetMapping
    public Collection<ItemDto> getItemsFromCart(
    ) {
        log.info("Запрос GET на получение товаров из корзины /cart");
        return cartService.getItemsFromCart();
    }

    @DeleteMapping("/{vendorCode}")
    public void removeItemFromCart(
            @PathVariable Long vendorCode
    ) {
        log.info("Запрос DELETE на удаление товара /cart/{}", vendorCode);
        cartService.removeItemFromCart(vendorCode);
    }

    @DeleteMapping
    public void cleanCart(
    ) {
        log.info("Запрос DELETE удаление товаров из корзины /cart");
        cartService.cleanCart();
    }

    @PostMapping("/buy")
    public void buyCart(
    ) {
        log.info("Запрос POST купить корзину /cart/buy");
        cartService.buyCart();
    }
}
