package ru.task.iss.cart.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.cart.service.dtos.CartItemDto;
import ru.task.iss.models.CartItem;
import ru.task.iss.models.SaleItem;

import java.util.Collection;

public interface CartService {
    Page<SaleItem> getSalesByVendorCode(Long vendorCode, Pageable pageable);

    CartItem findItemInCart(Long vendorCode);

    CartItem checkItemInCart(Long vendorCode);

    @Transactional
    CartItem addItemToCart(CartItem cartItem);

    @Transactional
    void removeItemFromCart(Long vendorCode);

    Collection<CartItem> getItemsFromCart();

    @Transactional
    void cleanCart();

    @Transactional
    void buyCart();
}
