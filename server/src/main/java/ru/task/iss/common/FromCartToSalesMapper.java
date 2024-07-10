package ru.task.iss.common;

import ru.task.iss.models.CartItem;
import ru.task.iss.models.SaleItem;

import java.time.LocalDateTime;

public class FromCartToSalesMapper {
    public static SaleItem fromCartToSale(CartItem cartItem){
        SaleItem saleItem =  new SaleItem();
        //sale.setSalesCode();
        saleItem.setName(cartItem.getName());
        saleItem.setPrice(cartItem.getPrice());
        saleItem.setAmount(cartItem.getAmount());
        //sale.setDiscount();
        //sale.setDiscountCode();
        //sale.setFinalPrice();
        //sale.setTotalPrice();
        saleItem.setCreatedOn(LocalDateTime.now());
        return saleItem;
    }
}
