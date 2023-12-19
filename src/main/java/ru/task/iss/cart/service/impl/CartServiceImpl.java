package ru.task.iss.cart.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.cart.repository.CartItemsRepository;
import ru.task.iss.cart.repository.SalesRepository;
import ru.task.iss.cart.service.CartService;
import ru.task.iss.discounts.repository.DiscountRepository;
import ru.task.iss.exceptions.BadRequestException;
import ru.task.iss.items.repositories.ItemsRepository;
import ru.task.iss.models.CartItem;
import ru.task.iss.models.Discount;
import ru.task.iss.models.Item;
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
        log.info("Добавление товара в корзину");
        CartItem cartItemToSave = cartItemsRepository.findByVendorCode(cartItem.getVendorCode());
        Item itemFromRepository = itemsRepository.findByVendorCode(cartItem.getVendorCode());
        validateAmount(itemFromRepository.getAmount(), itemFromRepository.getAmount());
        if (Optional.ofNullable(cartItemToSave).isPresent()) {
            cartItemToSave.setAmount(cartItem.getAmount());
        } else cartItemToSave = cartItem;
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
        Long salesCode = salesRepository.findMaximum();
        if (salesCode == null) {
            salesCode = 0L;
        }
        Collection<CartItem> cart = cartItemsRepository.findAll();
        salesCode++;
        Discount discount = discountRepository.findFirstByOrderByIdDesc();
        Double coefficient = 1.0;
        for (CartItem cartItem : cart) {
            if (discount.getItemVendorCode() == cartItem.getVendorCode()) {
                coefficient = (100 - discount.getCoefficient()) / 100;
            }
            Item itemNewAmount = itemsRepository.findByVendorCode(cartItem.getVendorCode());
            Sale sale = new Sale();
            sale.setSalesCode(salesCode);
            sale.setName(cartItem.getName());
            sale.setPrice(cartItem.getPrice());
            sale.setAmount(cartItem.getAmount());
            sale.setDiscount(discount.getCoefficient());
            sale.setDiscountCode(discount.getDiscountCode());
            sale.setFinalPrice(cartItem.getPrice() * coefficient);
            sale.setTotalPrice(cartItem.getPrice() * coefficient * cartItem.getAmount());
            sale.setCreatedOn(LocalDateTime.now());
            salesRepository.save(sale);
            itemNewAmount.setAmount(itemNewAmount.getAmount() - cartItem.getAmount());
            itemsRepository.save(itemNewAmount);
        }
        clearCart();
        log.info("Покупка корзины");
    }
}
