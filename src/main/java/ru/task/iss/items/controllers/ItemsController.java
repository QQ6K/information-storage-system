package ru.task.iss.items.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.task.iss.items.services.ItemService;
import ru.task.iss.items.services.dtos.ItemDto;
import ru.task.iss.items.services.dtos.ItemUpdateDto;

import java.util.List;

@RestController
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
public class ItemsController {

    private final ItemService itemService;

    @PostMapping
    public ItemUpdateDto createItem(
            @RequestBody ItemDto itemDto
    ) {
        return itemService.createItem(itemDto);
    }

    @GetMapping("/{itemId}")
    public ItemDto readItem(
            @PathVariable Long itemId
    ) {
        return itemService.readItem(itemId);
    }

    @GetMapping
    public List<ItemUpdateDto> getItems(
    ) {
        log.info("Получен GET запрос /items");
        return itemService.getItems();
    }

    @PatchMapping("/{itemId}")
    public ItemUpdateDto updateItem(
            @PathVariable Long itemId,
            @RequestBody ItemDto itemDto
    ) {
        return itemService.updateItem(itemId, itemDto);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(
            @PathVariable Long itemId
    ) {
        itemService.deleteItem(itemId);
    }

}
