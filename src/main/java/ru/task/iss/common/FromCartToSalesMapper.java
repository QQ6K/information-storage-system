package ru.task.iss.common;

import ru.task.iss.items.services.dtos.ItemDto;
import ru.task.iss.models.CartItem;
import ru.task.iss.models.Item;
import ru.task.iss.models.Sale;

import java.time.LocalDateTime;

public class FromCartToSalesMapper {
    public static Sale fromCartToSale(CartItem cartItem){
        Sale sale =  new Sale();
        //sale.setSalesCode();
        sale.setName(cartItem.getName());
        sale.setPrice(cartItem.getPrice());
        sale.setAmount(cartItem.getAmount());
        //sale.setDiscount();
        //sale.setDiscountCode();
        //sale.setFinalPrice();
        //sale.setTotalPrice();
        sale.setCreatedOn(LocalDateTime.now());
        return sale;
    }
}
