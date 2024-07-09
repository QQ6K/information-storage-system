package ru.task.iss.items.services;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.common.PageDTO;
import ru.task.iss.items.services.dtos.ItemDto;
import ru.task.iss.models.Item;

import java.util.List;

public interface ItemService {
    ItemDto createItem(ItemDto itemDto);

    Item readItem(Long vendorCode);

    List<ItemDto> getItems();

    PageDTO<Item> getItemsPage(Pageable pageable);

    @Transactional
    ItemDto updateItem(Long itemId, ItemDto itemDto);

    @Transactional
    void deleteItem(Long itemId);

    Item findItemInRepository(Long vendorCode);
}
