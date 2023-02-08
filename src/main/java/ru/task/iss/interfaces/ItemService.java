package ru.task.iss.interfaces;

import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.models.Item;
import ru.task.iss.services.dtos.ItemDto;
import ru.task.iss.services.dtos.ItemUpdateDto;

public interface ItemService {
    ItemUpdateDto createItem(ItemDto itemDto);

    ItemDto readItem(Long itemId);

    @Transactional
    ItemUpdateDto updateItem(Long itemId, ItemDto itemDto);

    @Transactional
    void deleteItem(Long itemId);

    Item findItemInRepository(Long itemId);
}
