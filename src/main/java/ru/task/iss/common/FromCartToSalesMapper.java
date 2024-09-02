package ru.task.iss.common;

import ru.task.iss.models.CartProduct;
import ru.task.iss.models.SaleProduct;

import java.time.LocalDateTime;

public class FromCartToSalesMapper {
    public static SaleProduct fromCartToSale(CartProduct cartProduct) {
        SaleProduct saleProduct = new SaleProduct();
        //sale.setSalesCode();
        saleProduct.setName(cartProduct.getName());
        saleProduct.setPrice(cartProduct.getPrice());
        saleProduct.setAmount(cartProduct.getAmount());
        //sale.setDiscount();
        //sale.setDiscountCode();
        //sale.setFinalPrice();
        //sale.setTotalPrice();
        saleProduct.setCreatedOn(LocalDateTime.now());
        return saleProduct;
    }
}
