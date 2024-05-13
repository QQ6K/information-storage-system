package ru.task.iss.items.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import ru.task.iss.cart.service.CartService;
import ru.task.iss.common.PageDTO;
import ru.task.iss.common.PageToPageDTOMapper;
import ru.task.iss.exceptions.CrudException;
import ru.task.iss.items.repositories.ItemsRepository;
import ru.task.iss.items.services.ItemService;
import ru.task.iss.items.services.dtos.ItemDto;
import ru.task.iss.items.services.dtos.ItemMapper;
import ru.task.iss.items.services.dtos.ItemUpdateDto;
import ru.task.iss.models.Item;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class ItemsServiceImpl implements ItemService {

    private final ItemsRepository itemsRepository;

    //private final CartService cartService;

    private final PageToPageDTOMapper<Item> pageToPageDTOMapper;

    @Override
    @Transactional
    public ItemUpdateDto createItem(ItemDto itemDto) {
        log.info("Создание товара");
        return ItemMapper.toUpdateDto(itemsRepository.save(ItemMapper.fromDto(itemDto)));
    }

    @Override
    public Item readItem(Long vendorCode) {
        //return ItemMapper.toDto(findItemInRepository(vendorCode));
        return findItemInRepository(vendorCode);
    }

    @Override
    public List<ItemUpdateDto> getItems() {
        log.info("Получить список товаров");
        return itemsRepository.findAll().stream().map(ItemMapper::toUpdateDto).collect(Collectors.toList());
    }

    @Override
    public PageDTO<Item> getItemsPage(Pageable pageable) {
        log.info("Получить страницу товаров");
        Page<Item> itemPage = itemsRepository.findAll(pageable);
        return pageToPageDTOMapper.pageToPageDTO(itemPage);
        //findAll().stream().map(ItemMapper::toUpdateDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ItemUpdateDto updateItem(Long itemId, ItemDto itemDto) {
        Item item = findItemInRepository(itemId);
        Optional.ofNullable(itemDto.getName()).ifPresent(item::setName);
        Optional.ofNullable(itemDto.getPrice()).ifPresent(item::setPrice);
        Optional.ofNullable(itemDto.getAmount()).ifPresent(item::setAmount);
        return ItemMapper.toUpdateDto(itemsRepository.save(item));
    }

    @Override
    @Transactional
    public void deleteItem(Long vendorId) {
        Item item = findItemInRepository(vendorId);
        //cartService.removeItemFromCart(vendorId);
        itemsRepository.delete(findItemInRepository(vendorId));
    }

    @Override
    public Item findItemInRepository(Long vendorCode) {
        Item item = itemsRepository.findByVendorCode(vendorCode)
                .orElseThrow(() -> new CrudException("Не удалось найти товар c артикулом = " + vendorCode));
        return item;
    }

}
