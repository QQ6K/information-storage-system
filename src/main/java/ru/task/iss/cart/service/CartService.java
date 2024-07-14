package ru.task.iss.cart.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.items.services.dtos.ItemDto;
import ru.task.iss.models.CartItem;
import ru.task.iss.statistics.services.dto.SaleItemDto;

import java.util.Collection;

public interface CartService {
    Page<SaleItemDto> getSalesByVendorCode(Long vendorCode, Pageable pageable);

    CartItem findItemInCart(Long vendorCode);

    CartItem checkItemInCart(Long vendorCode);

    @Transactional
    ItemDto addItemToCart(CartItem cartItem);

    @Transactional
    void removeItemFromCart(Long vendorCode);

    Collection<ItemDto> getItemsFromCart();

    @Transactional
    void cleanCart();

    @Transactional
    void buyCart();
}
