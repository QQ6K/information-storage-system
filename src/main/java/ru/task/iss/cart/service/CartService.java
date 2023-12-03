package ru.task.iss.cart.service;

import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.cart.service.dtos.CartItemDto;
import ru.task.iss.models.CartItem;

import java.util.Collection;

public interface CartService {
    @Transactional
    CartItem addItemToCart(CartItem cartItem);

    @Transactional
    void removeItemFromCart(Long vendorCode);

    Collection<CartItem> getItemsFromCart();

    @Transactional
    void cleanCart();
}
