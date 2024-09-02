package ru.task.iss.products.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.task.iss.common.PageDTO;
import ru.task.iss.exceptions.CrudException;
import ru.task.iss.products.services.ProductsService;
import ru.task.iss.products.services.dtos.ProductDto;
import ru.task.iss.models.Product;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/products")
@RequiredArgsConstructor
@Slf4j
public class ProductsController {

    private final ProductsService productsService;

    @PostMapping
    public ProductDto createProduct(
            @Valid @RequestBody ProductDto productDto
    ) {
        log.info("Запрос POST на создание товара /products");
        return productsService.createProduct(productDto);
    }

    @GetMapping("/{vendorCode}")
    public Product readProduct(
            @PathVariable Long vendorCode
    ) {
        return productsService.readProduct(vendorCode);
    }

    @GetMapping
    public PageDTO<Product> getProducts(
            @RequestParam(defaultValue = "0", required = false) Integer from,
            @RequestParam(defaultValue = "10", required = false) Integer size,
            @RequestParam(required = false) Integer page) {
        Pageable pageable;
        if (size == null) {
            pageable = Pageable.unpaged();
        } else if (size <= 0) {
            throw new CrudException("Ошибка параметров пагинации");
        } else {
            if (page == null) {
                page = from / size;
            }
            pageable = PageRequest.of(page, size);
        }
        log.info("Запрос GET /products?size={}&page={}", size, page);
        return productsService.getProductPage(pageable);
    }

    @PatchMapping("/{productId}")
    public ProductDto updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductDto productDto
    ) {
        log.info("Запрос PATCH на изменение товара id = {}", productId);
        return productsService.updateProduct(productId, productDto);
    }

    @DeleteMapping("/{vendorId}")
    public void deleteProduct(
            @PathVariable Long vendorId
    ) {
        productsService.deleteProduct(vendorId);
    }


}
