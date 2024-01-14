package ru.task.iss.cart.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.cart.repository.CartItemsRepository;
import ru.task.iss.cart.repository.SaleItemsRepository;
import ru.task.iss.cart.repository.SalesRepository;
import ru.task.iss.cart.service.CartService;
import ru.task.iss.discounts.repository.DiscountRepository;
import ru.task.iss.exceptions.BadRequestException;
import ru.task.iss.items.repositories.ItemsRepository;
import ru.task.iss.models.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartItemsRepository cartItemsRepository;

    private final SaleItemsRepository saleItemsRepository;

    private final SalesRepository salesRepository;

    private final ItemsRepository itemsRepository;

    private final DiscountRepository discountRepository;

    private void clearCart() {
        cartItemsRepository.deleteAll();
    }

    private void validateAmount(Long itemFromRepositoryAmount, Long cartItemToSaveAmount) {
        if (itemFromRepositoryAmount > cartItemToSaveAmount) {
            throw new BadRequestException("Количество в корзине больше, чем на складе!");
        }
    }

    @Transactional
    @Override
    public CartItem addItemToCart(CartItem cartItem) {
        CartItem cartItemToSave = cartItemsRepository.findByVendorCode(cartItem.getVendorCode());
        Item itemFromRepository = itemsRepository.findByVendorCode(cartItem.getVendorCode());
        validateAmount(itemFromRepository.getAmount(), itemFromRepository.getAmount());
        if (Optional.ofNullable(cartItemToSave).isPresent()) {
            cartItemToSave.setAmount(cartItem.getAmount());
        } else cartItemToSave = cartItem;
        log.info("Добавление товара в корзину {} ", cartItemToSave.getName());
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
        clearCart();
        log.info("Очистка корзины");
    }

    @Transactional
    @Override
    public void buyCart() {
        Long saleCode = saleItemsRepository.findMaximum();
        log.info("поковырялись в репозитории нашли КОД {}", saleCode);
        if (saleCode == null) {
            saleCode = 0L;
        }
        log.info("установлен прошлый КОД {}", saleCode);
        Collection<CartItem> cart = cartItemsRepository.findAll();
        saleCode++;
        Discount discount = discountRepository.findFirstByOrderByIdDesc();
        Double coefficient = 1.0;
        for (CartItem cartItem : cart) {
            if (discount.getItemVendorCode() == cartItem.getVendorCode()) {
                coefficient = (100 - discount.getCoefficient()) / 100;
            }
            Item itemNewAmount = itemsRepository.findByVendorCode(cartItem.getVendorCode());
            SaleItem saleItem = new SaleItem();
            saleItem.setSalesCode(saleCode);
            saleItem.setName(cartItem.getName());
            saleItem.setPrice(cartItem.getPrice());
            saleItem.setAmount(cartItem.getAmount());
            saleItem.setDiscount(discount.getCoefficient());
            saleItem.setDiscountCode(discount.getDiscountCode());
            saleItem.setFinalPrice(cartItem.getPrice() * coefficient);
            saleItem.setTotalPrice(cartItem.getPrice() * coefficient * cartItem.getAmount());
            saleItem.setCreatedOn(LocalDateTime.now());
            saleItemsRepository.save(saleItem);
            itemNewAmount.setAmount(itemNewAmount.getAmount() - cartItem.getAmount());
            itemsRepository.save(itemNewAmount);
        }
        clearCart();
        log.info("Покупка корзины");
    }

    @Transactional
    @Override
    public void formingSale(int saleCode) {
        Sale sale = new Sale();
        Collection<Sale> sales = saleItemsRepository.getSalesByCode(saleCode);
        salesRepository.save(sale);
    }
}
