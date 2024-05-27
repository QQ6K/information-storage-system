package ru.task.iss.items.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.task.iss.common.PageDTO;
import ru.task.iss.exceptions.BadRequestException;
import ru.task.iss.exceptions.CrudException;
import ru.task.iss.items.services.ItemService;
import ru.task.iss.items.services.dtos.ItemDto;
import ru.task.iss.items.services.dtos.ItemUpdateDto;
import ru.task.iss.models.Item;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
public class ItemsController {

    private final ItemService itemService;

    @PostMapping
    public ItemUpdateDto createItem(
            @Valid @RequestBody ItemDto itemDto
    ) {
        log.info("Запрос POST на создание товара /items");
        return itemService.createItem(itemDto);
    }

    @GetMapping("/{vendorCode}")
    public Item readItem(
            @PathVariable Long vendorCode
    ) {
        return itemService.readItem(vendorCode);
    }

    @GetMapping
    public PageDTO<Item> getItems(
            @RequestParam(defaultValue = "0", required = false) Integer from,
            @RequestParam(defaultValue = "10", required = false) Integer size,
            @RequestParam(required = false) Integer page) {
        Pageable pageable;
        if (size == null) {
            pageable = Pageable.unpaged();
        } else if (size <= 0) {
            throw new CrudException("Ошибка параметров пагинации");
        } else {
            if (page == null) {
                page = from / size;
            }
            pageable = PageRequest.of(page, size);
        }
        log.info("Запрос GET /items?size={}&page={}", size, page);
        return itemService.getItemsPage(pageable);
    }

    @PatchMapping("/{itemId}")
    public ItemUpdateDto updateItem(
            @PathVariable Long itemId,
            @RequestBody ItemDto itemDto
    ) {
        log.info("Запрос PATCH на изменение товара id = {}", itemId);
        return itemService.updateItem(itemId, itemDto);
    }

    @DeleteMapping("/{vendorId}")
    public void deleteItem(
            @PathVariable Long vendorId
    ) {
        itemService.deleteItem(vendorId);
    }


}
