package ru.task.iss.items.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.exceptions.CrudException;
import ru.task.iss.items.services.ItemService;
import ru.task.iss.models.Item;
import ru.task.iss.items.repositories.ItemsRepository;
import ru.task.iss.items.services.dtos.ItemDto;
import ru.task.iss.items.services.dtos.ItemUpdateDto;
import ru.task.iss.items.services.dtos.ItemMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class ItemsServiceImpl implements ItemService {

    private final ItemsRepository itemsRepository;

    @Override
    @Transactional
    public ItemUpdateDto createItem(ItemDto itemDto){
        return ItemMapper.toUpdateDto(itemsRepository.save(ItemMapper.fromDto(itemDto)));
    }

    @Override
    public ItemDto readItem(Long itemId){
        return ItemMapper.toDto(findItemInRepository(itemId));
    }

    @Override
    public List<ItemUpdateDto> getItems(){
        log.info("Получить список товаров");
        return itemsRepository.findAll().stream().map(ItemMapper::toUpdateDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ItemUpdateDto updateItem(Long itemId, ItemDto itemDto){
        Item item = findItemInRepository(itemId);
        Optional.ofNullable(itemDto.getName()).ifPresent(item::setName);
        Optional.ofNullable(itemDto.getPrice()).ifPresent(item::setPrice);
        return ItemMapper.toUpdateDto(itemsRepository.save(item));
    }

    @Override
    @Transactional
    public void deleteItem(Long itemId){
        itemsRepository.delete(findItemInRepository(itemId));
    }

    @Override
    public Item findItemInRepository(Long itemId){
        return itemsRepository.findById(itemId)
                .orElseThrow(() -> new CrudException("Cannot find Item with id = " + itemId));
    }

}
