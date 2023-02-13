package ru.task.iss.sales.services.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.exceptions.CrudException;
import ru.task.iss.items.repositories.ItemsRepository;
import ru.task.iss.models.Sale;
import ru.task.iss.models.SaleItem;
import ru.task.iss.sales.repositories.SaleItemsRepository;
import ru.task.iss.sales.repositories.SalesRepository;
import ru.task.iss.sales.services.SalesService;
import ru.task.iss.sales.services.dtos.*;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@EnableScheduling
@Transactional(readOnly = true)
public class SalesServiceImpl implements SalesService {

    private final SalesRepository salesRepository;

    private final SaleItemsRepository saleItemsRepository;

    private final ItemsRepository itemsRepository;

    //@Override
    @Transactional
    public SaleItemDto createSaleItem(SaleItemDto saleItemDto){
        return SaleItemMapper.toDto(saleItemsRepository.save(SaleItemMapper.fromDto(saleItemDto)));
    }

    //@Override
    public SaleItemDto readSaleItem(Long saleItemId){
        return SaleItemMapper.toDto(findSaleItemInRepository(saleItemId));
    }

   //@Override
    @Transactional
    public SaleItemUpdateDto updateSaleItem(Long saleItemId, SaleItemDto saleItemDto){
        SaleItem saleItem = findSaleItemInRepository(saleItemId);
        Optional.ofNullable(saleItemDto.getItem()).ifPresent(saleItem::setItem);
        Optional.ofNullable(saleItemDto.getSale()).ifPresent(saleItem::setSale);
        Optional.ofNullable(saleItemDto.getCount()).ifPresent(saleItem::setCount);
        Optional.ofNullable(saleItemDto.getDiscount()).ifPresent(saleItem::setDiscount);
        return SaleItemMapper.toUpdateDto(saleItemsRepository.save(SaleItemMapper.fromDto(saleItemDto)));
    }

    //@Override
    @Transactional
    public void deleteSaleItem(Long saleItemId){
        saleItemsRepository.delete(findSaleItemInRepository(saleItemId));
    }

    //@Override
    public SaleItem findSaleItemInRepository(Long saleId){
        return saleItemsRepository.findById(saleId)
                .orElseThrow(()->new CrudException("Cannot SaleItem with id = " + saleId));
    }


    @Override
    @Transactional
    public SaleDto createSale(SaleDto saleDto){
        return SaleMapper.toDto(salesRepository.save(SaleMapper.fromDto(saleDto)));
    }

    @Override
    public SaleDto readSale(Long saleId){
        return SaleMapper.toDto(findSaleInRepository(saleId));
    }

    @Override
    @Transactional
    public SaleUpdateDto updateSale(Long saleId, SaleDto saleDto){
        Sale sale = findSaleInRepository(saleId);
        Optional.ofNullable(saleDto.getCreatedOn()).ifPresent(sale::setCreatedOn);
        Optional.ofNullable(saleDto.getShoppingList()).ifPresent(sale::setShoppingList);
        return SaleMapper.toUpdateDto(salesRepository.save(SaleMapper.fromDto(saleDto)));
    }

    @Override
    @Transactional
    public void deleteSale(Long saleId){
        saleItemsRepository.delete(findSaleItemInRepository(saleId));
    }

    @Override
    public Sale findSaleInRepository(Long saleId){
        return salesRepository.findById(saleId)
                .orElseThrow(()->new CrudException("Cannot SaleItem with id = " + saleId));
    }

}
