package ru.task.iss.products.services.dtos;

import ru.task.iss.models.CartProduct;
import ru.task.iss.models.Product;

public class ProductMapper {

    public static ProductDto toDto(Product product) {
        return new ProductDto(product.getVendorCode(), product.getName(), product.getPrice() / 100, product.getAmount());
    }

//    public static ItemDto toDto(Item item){
//        return new ItemDto(item.getVendorCode(), item.getName(), item.getPrice()/100, item.getAmount());
//    }

    public static Product fromDto(ProductDto productDto) {
        Product product = new Product();
        product.setVendorCode(productDto.getVendorCode());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice() * 100);
        product.setAmount(productDto.getAmount());
        return product;
    }

    public static Product fromUpdateDto(ProductDto productDto) {
        Product product = new Product();
        product.setId(product.getId());
        product.setVendorCode(product.getVendorCode());
        product.setName(productDto.getName());
        product.setPrice((productDto.getPrice() * 100));
        return product;
    }

    public static ProductDto toProductDtoFromCartProduct(CartProduct cartProduct) {
        return new ProductDto(cartProduct.getVendorCode(), cartProduct.getName(), cartProduct.getPrice() / 100, cartProduct.getAmount());
    }


}
