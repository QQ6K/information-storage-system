package ru.task.iss.cart.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.products.services.dtos.ProductDto;
import ru.task.iss.models.CartProduct;
import ru.task.iss.statistics.services.dto.SaleProductDto;

import java.util.Collection;

public interface CartService {
    Page<SaleProductDto> getSalesByVendorCode(Long vendorCode, Pageable pageable);

    CartProduct findProductInCart(Long vendorCode);

    CartProduct checkProductInCart(Long vendorCode);

    @Transactional
    ProductDto addProductToCart(CartProduct cartProduct);

    @Transactional
    void removeProductFromCart(Long vendorCode);

    Collection<ProductDto> getProductFromCart();

    @Transactional
    void cleanCart();

    @Transactional
    void buyCart();
}
