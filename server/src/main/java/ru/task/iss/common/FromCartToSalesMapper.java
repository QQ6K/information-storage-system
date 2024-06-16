package ru.task.iss.common;

import ru.task.iss.items.services.dtos.ItemDto;
import ru.task.iss.models.CartItem;
import ru.task.iss.models.Item;
import ru.task.iss.models.Sale;
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
        saleItem.setCreatedOn(DateTimeFormatterCustom.formatLocalDateTime(LocalDateTime.now()));
        return saleItem;
    }
}
