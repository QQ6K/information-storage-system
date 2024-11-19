package ru.task.miss.interfaces;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.task.miss.common.PageDTO;
import ru.task.miss.dtos.ProductDto;
import ru.task.miss.models.Product;

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
