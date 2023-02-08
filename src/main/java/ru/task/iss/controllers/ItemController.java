package ru.task.iss.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.task.iss.interfaces.ItemService;
import ru.task.iss.services.dtos.ItemDto;
import ru.task.iss.services.dtos.ItemUpdateDto;

@RestController
@RequestMapping(path = "/item")
@RequiredArgsConstructor
public class ItemController {

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
