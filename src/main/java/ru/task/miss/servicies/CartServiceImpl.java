package ru.task.miss.servicies;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.task.miss.repositories.CartProductsRepository;
import ru.task.miss.repositories.SaleProductsRepository;
import ru.task.miss.repositories.SalesRepository;
import ru.task.miss.interfaces.CartService;
import ru.task.miss.repositories.DiscountRepository;
import ru.task.miss.exceptions.CrudException;
import ru.task.miss.repositories.ProductsRepository;
import ru.task.miss.interfaces.ProductsService;
import ru.task.miss.dtos.ProductDto;
import ru.task.miss.mappers.ProductMapper;
import ru.task.miss.models.*;
import ru.task.miss.dtos.SaleProductDto;
import ru.task.miss.mappers.SaleProductMapper;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class CartServiceImpl implements CartService {

    private final ProductsService productsService;

    private final CartProductsRepository cartProductsRepository;

    private final SaleProductsRepository saleProductsRepository;

    private final SalesRepository salesRepository;

    private final ProductsRepository productsRepository;

    private final DiscountRepository discountRepository;

    private void clearCart() {
        cartProductsRepository.deleteAll();
    }

    private void validateAmount(Long productFromRepositoryAmount, Long cartProductToSaveAmount) {
        if (productFromRepositoryAmount < cartProductToSaveAmount) {
            throw new CrudException("Количество в корзине больше, чем на складе!");
        }
    }

    @Override
    public Page<SaleProductDto> getSalesByVendorCode(Long vendorCode, Pageable pageable) {
        Page<SaleProduct> saleProductPage = saleProductsRepository
                .findByVendorCode(vendorCode, pageable);
        List<SaleProductDto> saleProductDtos = saleProductPage.getContent().stream()
                .map(SaleProductMapper::toSaleProductDto)
                .collect(Collectors.toList());
        return new PageImpl<>(saleProductDtos, pageable, saleProductPage.getTotalElements());
    }

    @Override
    public CartProduct findProductInCart(Long vendorCode) {
        CartProduct cartProduct = cartProductsRepository.getCartProductByVendorCode(vendorCode);
        return cartProduct;
    }

    @Override
    public CartProduct checkProductInCart(Long vendorCode) {
        CartProduct cartProduct = cartProductsRepository
                .findByVendorCode(vendorCode).orElseThrow(() -> new CrudException(
                        "В корзине нет товара c артикулом = " + vendorCode));
        return cartProduct;
    }

    @Transactional
    @Override
    public ProductDto addProductToCart(CartProduct cartProduct) {
        CartProduct cartProductToSave = findProductInCart(cartProduct.getVendorCode());
        Product productFromRepository = productsService.findProductInRepository(cartProduct.getVendorCode());
        validateAmount(productFromRepository.getAmount(), cartProduct.getAmount());
        cartProduct.setProductId(productFromRepository.getId());
        if (Optional.ofNullable(cartProductToSave).isPresent()) {
            cartProductToSave.setAmount(cartProduct.getAmount());
        } else cartProductToSave = cartProduct;
        log.info("Добавление товара в корзину {} ", cartProductToSave.getName());
        return ProductMapper.toProductDtoFromCartProduct(cartProductsRepository.save(cartProductToSave));
    }

    @Transactional
    @Override
    public void removeProductFromCart(Long vendorCode) {
        checkProductInCart(vendorCode);
        log.info("Удаление товара из корзины");
        cartProductsRepository.deleteCartProductByVendorCode(vendorCode);
    }

    @Override
    public Collection<ProductDto> getProductFromCart() {
        log.info("Получение товаров из корзины");
        Collection<CartProduct> cartProducts = cartProductsRepository.findAll();
        Collection<ProductDto> response = cartProducts.stream().map(ProductMapper::toProductDtoFromCartProduct).collect(Collectors.toList());
        return response;
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
        try {

            Long saleCode = saleProductsRepository.findMaximum();
            log.info("поковырялись в репозитории нашли КОД {}", saleCode);
            if (saleCode == null) {
                saleCode = 1L;
            }
            log.info("установлен прошлый КОД {}", saleCode);
            Collection<CartProduct> cart = cartProductsRepository.findAll();
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

            for (CartProduct cartProduct : cart) {
                Product product = productsService.findProductInRepository(cartProduct.getVendorCode());
                if (product == null) {
                    removeProductFromCart(cartProduct.getVendorCode());
                } else {
                    SaleProduct saleProduct = new SaleProduct();
                    if (Objects.equals(discount.getProductVendorCode(), cartProduct.getVendorCode())) {
                        coefficient = (100 - discount.getCoefficient());
                        saleProduct.setDiscountCode(discount.getDiscountCode());
                        saleProduct.setDiscount(Long.valueOf(coefficient));
                    } else {
                        saleProduct.setDiscountCode(0L);
                        saleProduct.setDiscount(100L);
                        coefficient = 100;
                    }
                    Product productNewAmount = productsService.findProductInRepository(cartProduct.getVendorCode());

                    if (cartProduct.getAmount() > productNewAmount.getAmount())
                        throw new CrudException("Товар: " + productNewAmount.getName() + ". На складе: " + productNewAmount.getAmount()
                                + " шт. Невозможно купить: " + cartProduct.getAmount() + " шт.");

                    saleProduct.setVendorCode(cartProduct.getVendorCode());
                    saleProduct.setSaleCode(saleCode);
                    saleProduct.setName(cartProduct.getName());
                    saleProduct.setPrice(productNewAmount.getPrice()); // один товар
                    saleProduct.setAmount(cartProduct.getAmount());
                    saleProduct.setFinalPrice(productNewAmount.getPrice() * coefficient / 100);
                    long price = saleProduct.getFinalPrice();
                    saleProduct.setTotalPrice(price * cartProduct.getAmount());
                    saleProduct.setCreatedOn(LocalDateTime.now());
                    saleProduct.setProductId(cartProduct.getProductId());

                    sale.setPrice(sale.getPrice() + saleProduct.getPrice() * cartProduct.getAmount());
                    sale.setFinalPrice(sale.getFinalPrice() + saleProduct.getTotalPrice());
                    sale.setDiscountSum(sale.getPrice() - sale.getFinalPrice());
                    sale.setDiscountCode(saleProduct.getDiscountCode());

                    saleProductsRepository.save(saleProduct);
                    productNewAmount.setAmount(productNewAmount.getAmount() - cartProduct.getAmount());
                    productsRepository.save(productNewAmount);
                }
            }
            clearCart();
            log.info("Покупка корзины");
            sale.setCreatedOn(LocalDateTime.now());
            salesRepository.save(sale);
        } catch (Exception e) {
            log.error("Ошибка при покупке корзины: {}", e.getMessage(), e);
        } finally {
            try {
                clearCart();
            } catch (Exception e) {
                log.error("Ошибка при очистке корзины: {}", e.getMessage(), e);
            }
            log.info("Завершение операции покупки корзины товаров");
        }
    }
}
