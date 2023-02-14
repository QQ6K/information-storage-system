package ru.task.iss.sales.services;

import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.models.Basket;
import ru.task.iss.models.Order;
import ru.task.iss.sales.services.dtos.UpdateBucketShortDto;

public interface SalesService {
    @Transactional
    Basket addItemToBucket(UpdateBucketShortDto updateBucketShortDto);

    //@Override
    @Transactional
    void deleteBucketItem(Long itemId);

    @Transactional
    Order buyBasket();
    /*@Transactional
    SaleDto createSale(SaleDto saleDto);

    SaleDto readSale(Long saleId);

    @Transactional
    SaleUpdateDto updateSale(Long saleId, SaleDto saleDto);

    @Transactional
    void deleteSale(Long saleId);

    Bucket findSaleInRepository(Long saleId);*/
}
