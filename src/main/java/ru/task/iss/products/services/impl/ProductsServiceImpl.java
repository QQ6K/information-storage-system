package ru.task.iss.products.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.common.PageDTO;
import ru.task.iss.common.PageToPageDTOMapper;
import ru.task.iss.exceptions.CrudException;
import ru.task.iss.products.repositories.ProductsRepository;
import ru.task.iss.products.services.ProductsService;
import ru.task.iss.products.services.dtos.ProductDto;
import ru.task.iss.products.services.dtos.ProductMapper;
import ru.task.iss.models.Product;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class ProductsServiceImpl implements ProductsService {

    private final ProductsRepository productsRepository;

    private final PageToPageDTOMapper<Product> pageToPageDTOMapper;

    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        log.info("Создание товара");
        Optional<Product> product = productsRepository.findByVendorCode(productDto.getVendorCode());
        if (product.isPresent()) {
            throw new CrudException("Товар с таким артикулом существует");
        }
        return ProductMapper.toDto(productsRepository.save(ProductMapper.fromDto(productDto)));
    }

    @Override
    public Product readProduct(Long vendorCode) {
        //return ItemMapper.toDto(findItemInRepository(vendorCode));
        return findProductInRepository(vendorCode);
    }

    @Override
    public List<ProductDto> getProduct() {
        log.info("Получить список товаров");
        return productsRepository.findAll().stream().map(ProductMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public PageDTO<Product> getProductPage(Pageable pageable) {
        log.info("Получить страницу товаров");
        Page<Product> productPage = productsRepository.findAll(pageable);
        return pageToPageDTOMapper.pageToPageDTO(productPage);
        //findAll().stream().map(ItemMapper::toUpdateDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductDto updateProduct(Long productId, ProductDto productDto) {
        Product product = findProductInRepository(productId);
        Optional.ofNullable(productDto.getName()).ifPresent(product::setName);
        Optional.ofNullable(productDto.getPrice()).ifPresent(product::setPrice);
        Optional.ofNullable(productDto.getAmount()).ifPresent(product::setAmount);
        return ProductMapper.toDto(productsRepository.save(product));
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        Product product = findProductInRepository(productId);
        //cartService.removeItemFromCart(vendorId);
        productsRepository.delete(findProductInRepository(productId));
    }

    @Override
    public Product findProductInRepository(Long vendorCode) {
        Product product = productsRepository.findByVendorCode(vendorCode)
                .orElseThrow(() -> new CrudException("Не удалось найти товар c артикулом = " + vendorCode));
        return product;
    }
}
