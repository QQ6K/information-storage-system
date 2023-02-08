package ru.task.iss.services;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.exceptions.CrudException;
import ru.task.iss.interfaces.ItemService;
import ru.task.iss.models.Item;
import ru.task.iss.repositories.ItemRepository;
import ru.task.iss.services.dtos.ItemDto;
import ru.task.iss.services.dtos.ItemUpdateDto;
import ru.task.iss.services.utilities.ItemMapper;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@EnableScheduling
@Transactional(readOnly = true)
public class ItemsServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public ItemUpdateDto createItem(ItemDto itemDto){
        return ItemMapper.toUpdateDto(itemRepository.save(ItemMapper.fromDto(itemDto)));
    }

    @Override
    public ItemDto readItem(Long itemId){
        return ItemMapper.toDto(findItemInRepository(itemId));
    }

    @Override
    @Transactional
    public ItemUpdateDto updateItem(Long itemId, ItemDto itemDto){
        Item item = findItemInRepository(itemId);
        Optional.ofNullable(itemDto.getName()).ifPresent(item::setName);
        Optional.ofNullable(itemDto.getPrice()).ifPresent(item::setPrice);
        return ItemMapper.toUpdateDto(itemRepository.save(item));
    }

    @Override
    @Transactional
    public void deleteItem(Long itemId){
        itemRepository.delete(findItemInRepository(itemId));
    }

    @Override
    public Item findItemInRepository(Long itemId){
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new CrudException("Cannot find Item with id = " + itemId));
    }

}
