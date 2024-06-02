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
import ru.task.iss.exceptions.CrudException;
import ru.task.iss.items.repositories.ItemsRepository;
import ru.task.iss.items.services.ItemService;
import ru.task.iss.models.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class CartServiceImpl implements CartService {

    private final ItemService itemService;

    private final CartItemsRepository cartItemsRepository;

    private final SaleItemsRepository saleItemsRepository;

    private final SalesRepository salesRepository;

    private final ItemsRepository itemsRepository;

    private final DiscountRepository discountRepository;

    private void clearCart() {
        cartItemsRepository.deleteAll();
    }

    private void validateAmount(Long itemFromRepositoryAmount, Long cartItemToSaveAmount) {
        if (itemFromRepositoryAmount < cartItemToSaveAmount) {
            throw new CrudException("Количество в корзине больше, чем на складе!");
        }
    }

    @Override
    public CartItem findItemInCart(Long vendorCode) {
        CartItem cartItem = cartItemsRepository.getCartItemByVendorCode(vendorCode);
        return cartItem;
    }

    @Override
    public CartItem checkItemInCart(Long vendorCode) {
        CartItem cartItem = cartItemsRepository
                .findByVendorCode(vendorCode).orElseThrow(() -> new CrudException(
                        "В корзине нет товара c артикулом = " + vendorCode));
        return cartItem;
    }

    @Transactional
    @Override
    public CartItem addItemToCart(CartItem cartItem) {
        CartItem cartItemToSave = findItemInCart(cartItem.getVendorCode());
        Item itemFromRepository = itemService.findItemInRepository(cartItem.getVendorCode());
        validateAmount(itemFromRepository.getAmount(), cartItem.getAmount());
        cartItem.setItemId(itemFromRepository.getId());
        if (Optional.ofNullable(cartItemToSave).isPresent()) {
            cartItemToSave.setAmount(cartItem.getAmount());
        } else cartItemToSave = cartItem;
        log.info("Добавление товара в корзину {} ", cartItemToSave.getName());
        return cartItemsRepository.save(cartItemToSave);
    }

    @Transactional
    @Override
    public void removeItemFromCart(Long vendorCode) {
        checkItemInCart(vendorCode);
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
        Integer coefficient;

        Sale sale = Sale.builder()
                .salesCode(saleCode)
                .price(0L)
                .finalPrice(0L)
                .discountSum(0L)
                .discountCode(0L)
                .build();

        for (CartItem cartItem : cart) {
            Item item = itemService.findItemInRepository(cartItem.getVendorCode());
            if (item == null) {
                removeItemFromCart(cartItem.getVendorCode());
            } else {
                SaleItem saleItem = new SaleItem();
                if (Objects.equals(discount.getItemVendorCode(), cartItem.getVendorCode())) {
                    coefficient = (100 - discount.getCoefficient());
                    saleItem.setDiscountCode(discount.getDiscountCode());
                    saleItem.setDiscount(Long.valueOf(coefficient));
                } else {
                    saleItem.setDiscountCode(0L);
                    saleItem.setDiscount(100L);
                    coefficient = 100;
                }
                Item itemNewAmount = itemService.findItemInRepository(cartItem.getVendorCode());

                if (cartItem.getAmount() > itemNewAmount.getAmount())
                    throw new CrudException("Товар: " + itemNewAmount.getName() + ". На складе: " + itemNewAmount.getAmount()
                            + " шт. Невозможно купить: " + cartItem.getAmount() + " шт.");

                saleItem.setVendorCode(cartItem.getVendorCode());
                saleItem.setSaleCode(saleCode);
                saleItem.setName(cartItem.getName());
                saleItem.setPrice(itemNewAmount.getPrice());
                saleItem.setAmount(cartItem.getAmount());
                saleItem.setFinalPrice(itemNewAmount.getPrice() * coefficient / 100);
                saleItem.setTotalPrice(saleItem.getFinalPrice() * cartItem.getAmount());
                saleItem.setCreatedOn(LocalDateTime.now());
                saleItem.setItemId(cartItem.getItemId());

                sale.setPrice(sale.getPrice() + saleItem.getPrice() * cartItem.getAmount());
                sale.setFinalPrice( sale.getFinalPrice() + saleItem.getTotalPrice());
                sale.setDiscountSum(sale.getPrice() - sale.getFinalPrice());
                sale.setDiscountCode(saleItem.getDiscountCode());

                saleItemsRepository.save(saleItem);
                itemNewAmount.setAmount(itemNewAmount.getAmount() - cartItem.getAmount());
                itemsRepository.save(itemNewAmount);
            }
        }
        clearCart();
        log.info("Покупка корзины");
        sale.setCreatedOn(LocalDateTime.now());
        salesRepository.save(sale);
    }
}
