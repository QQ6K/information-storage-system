package ru.task.iss.cart.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.cart.repository.CartItemsRepository;
import ru.task.iss.cart.repository.SalesRepository;
import ru.task.iss.cart.service.CartService;
import ru.task.iss.cart.service.dtos.CartItemDto;
import ru.task.iss.common.FromCartToSalesMapper;
import ru.task.iss.models.CartItem;
import ru.task.iss.models.Sale;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartItemsRepository cartItemsRepository;

    private final SalesRepository salesRepository;

    private void clearCart(){
        cartItemsRepository.deleteAll();
    }

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


    @Transactional
    @Override
    public void cleanCart() {
        log.info("Очистка корзины");
        clearCart();
    }

    @Transactional
    @Override
    public void buyCart() {
        Long salesCode = salesRepository.findMaximum();
        if (salesCode == null) {salesCode = 0L;}
        Collection<CartItem> cart= cartItemsRepository.findAll();
        salesCode++;
        for (CartItem cartItem: cart) {
            Sale sale = new Sale();
            sale.setSalesCode(salesCode);
            sale.setName(cartItem.getName());
            sale.setPrice(cartItem.getPrice());
            sale.setAmount(cartItem.getAmount());
            sale.setDiscount(0);
            sale.setDiscountCode(0L);
            sale.setFinalPrice(0);
            sale.setTotalPrice(0);
            sale.setCreatedOn(LocalDateTime.now());
            salesRepository.save(sale);
        }
        clearCart();
    }
}
