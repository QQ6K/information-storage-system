package ru.task.iss.services.utilities;

import ru.task.iss.models.Item;
import ru.task.iss.services.dtos.ItemDto;
import ru.task.iss.services.dtos.ItemUpdateDto;

public class ItemMapper {

    public static ItemUpdateDto toUpdateDto(Item item){
        return new ItemUpdateDto(item.getId(),item.getName(),item.getPrice());
    }

    public static ItemDto toDto(Item item){
        return new ItemDto(item.getName(),item.getPrice());
    }

    public static Item fromDto(ItemDto itemDto){
        Item item =  new Item();
        item.setName(itemDto.getName());
        item.setPrice(itemDto.getPrice());
        return item;
    }

    public static Item fromUpdateDto(ItemUpdateDto itemUpdateDto){
        Item item =  new Item();
        item.setId(item.getId());
        item.setName(itemUpdateDto.getName());
        item.setPrice(itemUpdateDto.getPrice());
        return item;
    }
}
