package ru.task.iss.items.services.dtos;

import ru.task.iss.models.CartItem;
import ru.task.iss.models.Item;

public class ItemMapper {

    public static ItemDto toDto(Item item) {
        return new ItemDto(item.getVendorCode(), item.getName(), item.getPrice() / 100, item.getAmount());
    }

//    public static ItemDto toDto(Item item){
//        return new ItemDto(item.getVendorCode(), item.getName(), item.getPrice()/100, item.getAmount());
//    }

    public static Item fromDto(ItemDto itemDto) {
        Item item = new Item();
        item.setVendorCode(itemDto.getVendorCode());
        item.setName(itemDto.getName());
        item.setPrice(itemDto.getPrice() * 100);
        item.setAmount(itemDto.getAmount());
        return item;
    }

    public static Item fromUpdateDto(ItemDto itemDto) {
        Item item = new Item();
        item.setId(item.getId());
        item.setVendorCode(item.getVendorCode());
        item.setName(itemDto.getName());
        item.setPrice((itemDto.getPrice() * 100));
        return item;
    }

    public static ItemDto toItemDtoFromCartItem(CartItem cartItem) {
        return new ItemDto(cartItem.getVendorCode(), cartItem.getName(), cartItem.getPrice() / 100, cartItem.getAmount());
    }


}
