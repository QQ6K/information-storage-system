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

        Sale sale = Sale.builder()
                .salesCode(saleCode)
                .price(0.)
                .finalPrice(0.)
                .discountSum(0.)
                .discountCode(0L)
                .build();

        for (CartItem cartItem : cart) {
            SaleItem saleItem = new SaleItem();
            if (discount.getItemVendorCode() == cartItem.getVendorCode()) {
                coefficient = (100 - discount.getCoefficient()) / 100;
                saleItem.setDiscountCode(discount.getDiscountCode());
                saleItem.setDiscount(coefficient);
            } else {saleItem.setDiscountCode(0L);
                saleItem.setDiscount(1);
            };
            Item itemNewAmount = itemsRepository.findByVendorCode(cartItem.getVendorCode());
            saleItem.setVendorCode(cartItem.getVendorCode());
            saleItem.setSaleCode(saleCode);
            saleItem.setName(cartItem.getName());
            saleItem.setPrice(cartItem.getPrice());
            saleItem.setAmount(cartItem.getAmount());
            saleItem.setFinalPrice(cartItem.getPrice() * coefficient);
            saleItem.setTotalPrice(cartItem.getPrice() * coefficient * cartItem.getAmount());
            saleItem.setCreatedOn(LocalDateTime.now());
            saleItem.setItemId(cartItem.getItemId()); //!

            sale.setPrice(sale.getPrice() + saleItem.getPrice() * cartItem.getAmount());
            sale.setFinalPrice(sale.getFinalPrice() + cartItem.getPrice() * coefficient * cartItem.getAmount());
            sale.setDiscountSum(sale.getDiscountSum() + sale.getPrice() - sale.getFinalPrice());
            sale.setDiscountCode(saleItem.getDiscountCode());

            saleItemsRepository.save(saleItem);
            itemNewAmount.setAmount(itemNewAmount.getAmount() - cartItem.getAmount());
            itemsRepository.save(itemNewAmount);
        }
        clearCart();
        log.info("Покупка корзины");
        sale.setCreatedOn(LocalDateTime.now());
        salesRepository.save(sale);
    }
}
