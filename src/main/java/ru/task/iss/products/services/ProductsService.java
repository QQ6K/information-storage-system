package ru.task.iss.products.services;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.common.PageDTO;
import ru.task.iss.products.services.dtos.ProductDto;
import ru.task.iss.models.Product;

import java.util.List;

public interface ProductsService {
    ProductDto createProduct(ProductDto productDto);

    Product readProduct(Long vendorCode);

    List<ProductDto> getProduct();

    PageDTO<Product> getProductPage(Pageable pageable);

    @Transactional
    ProductDto updateProduct(Long productId, ProductDto productDto);

    @Transactional
    void deleteProduct(Long productId);

    Product findProductInRepository(Long vendorCode);
}
