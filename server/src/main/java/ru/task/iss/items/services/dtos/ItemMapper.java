package ru.task.iss.items.services.dtos;

import ru.task.iss.models.Item;

public class ItemMapper {

    public static ItemUpdateDto toUpdateDto(Item item){
        return new ItemUpdateDto(item.getId(), item.getVendorCode(), item.getName(),item.getPrice(), item.getAmount());
    }

    public static ItemDto toDto(Item item){
        return new ItemDto(item.getVendorCode(), item.getName(), item.getPrice(), item.getAmount());
    }

    public static Item fromDto(ItemDto itemDto){
        Item item =  new Item();
        item.setVendorCode(itemDto.getVendorCode());
        item.setName(itemDto.getName());
        item.setPrice(itemDto.getPrice());
        item.setAmount(itemDto.getAmount());
        return item;
    }

    public static Item fromUpdateDto(ItemUpdateDto itemUpdateDto){
        Item item =  new Item();
        item.setId(item.getId());
        item.setVendorCode(item.getVendorCode());
        item.setName(itemUpdateDto.getName());
        item.setPrice(itemUpdateDto.getPrice());
        return item;
    }


}
