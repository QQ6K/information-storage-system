package ru.task.iss.cart.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.cart.repository.CartItemsRepository;
import ru.task.iss.cart.service.CartService;
import ru.task.iss.cart.service.dtos.CartItemDto;
import ru.task.iss.models.CartItem;

import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartItemsRepository cartItemsRepository;

    @Transactional
    @Override
    public CartItem addItemToCart(CartItem cartItem) {
        log.info("Добавление товара в корзину");
        CartItem cartItemToSave = cartItemsRepository.findByVendorCode(cartItem.getVendorCode());
        if (Optional.ofNullable(cartItemToSave).isPresent()) {
            cartItemToSave.setAmount(cartItem.getAmount());
        }
        else cartItemToSave = cartItem;
        return cartItemsRepository.save(cartItemToSave);
    }

    @Transactional
    @Override
    public void removeItemFromCart(Long vendorCode) {
        log.info("Удаление товара из корзины");
        cartItemsRepository.deleteCartItemByVendorCode(vendorCode);
    }

    @Override
    public Collection<CartItem> getItemsFromCart() {
        log.info("Получение товаров из корзины");
        return cartItemsRepository.findAll();
    }


    @Override
    @Transactional
    public void cleanCart() {
        log.info("Очистка корзины");
        cartItemsRepository.deleteAll();
    }
}
