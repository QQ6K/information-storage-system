package ru.task.iss.items.services;

import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.models.Item;
import ru.task.iss.items.services.dtos.ItemDto;
import ru.task.iss.items.services.dtos.ItemUpdateDto;

import java.util.List;

public interface ItemService {
    ItemUpdateDto createItem(ItemDto itemDto);

    ItemDto readItem(Long itemId);

    List<ItemUpdateDto> getItems();

    @Transactional
    ItemUpdateDto updateItem(Long itemId, ItemDto itemDto);

    @Transactional
    void deleteItem(Long itemId);

    Item findItemInRepository(Long itemId);
}
