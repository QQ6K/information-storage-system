package ru.task.iss.sales.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.discounts.scheduler.DiscountSchedulerService;
import ru.task.iss.exceptions.CrudException;
import ru.task.iss.items.repositories.ItemsRepository;
import ru.task.iss.models.*;
import ru.task.iss.sales.repositories.BasketItemsRepository;
import ru.task.iss.sales.repositories.BasketRepository;
import ru.task.iss.sales.repositories.OrderItemRepository;
import ru.task.iss.sales.repositories.OrderRepository;
import ru.task.iss.sales.services.SalesService;
import ru.task.iss.sales.services.dtos.UpdateBucketShortDto;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Service
@EnableScheduling
@Transactional(readOnly = true)
public class SalesServiceImpl implements SalesService {

    public static final Long userId = 1L;
    private final BasketRepository basketRepository;

    private final BasketItemsRepository basketItemsRepository;

    private final ItemsRepository itemsRepository;

    public final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;


    private final DiscountSchedulerService discountSchedulerService;

    @PostConstruct
    private Basket createBucketForUser() {
        Basket basket = new Basket();
        return basketRepository.save(basket);
    }

    @Override
    @Transactional
    public Basket addItemToBucket(UpdateBucketShortDto updateBucketShortDto) {
        Basket basket = basketRepository.findById(userId).orElseThrow(() -> new CrudException("This is impossible!"));
        BasketItem basketItem = basketItemsRepository.findBucketItemByItemId(updateBucketShortDto.getId());
        if (basketItem.equals(null)) {
            basketItem = new BasketItem();
        }
        if (updateBucketShortDto.getCount() == 0) {
            deleteBucketItemFromBucket(updateBucketShortDto.getId());
        } else {
            basketItem.setBasket(basket);
            basketItem.setItem(itemsRepository.getById(updateBucketShortDto.getId()));
            basketItem.setCount(updateBucketShortDto.getCount());
            basketItemsRepository.save(basketItem);
        }
        return basketRepository.findById(userId).orElseThrow(() -> new CrudException("This is impossible!"));
    }

    //@Override
    @Transactional
    @Override
    public void deleteBucketItem(Long itemId) {
        deleteBucketItemFromBucket(itemId);
    }

    public void deleteBucketItemFromBucket(Long itemId) {
        basketItemsRepository.delete(findBucketItemInRepository(itemId));
    }

    public BasketItem findBucketItemInRepository(Long bucketItemId) {
        return basketItemsRepository.findBucketItemByItemId(bucketItemId);
    }

    @Transactional
    @Override
    public Order buyBasket() {
        Basket basket = basketRepository.findById(userId).orElseThrow(() -> new CrudException("This is impossible!"));
        Order order = orderRepository.save(new Order());
        for (BasketItem bucketitem : basket.getBasketItems()) {
            order.getOrderItems().add(saveBasketItem(bucketitem, order));
        }
        return orderRepository.save(order);
    }

    private OrderItem saveBasketItem(BasketItem basketItem, Order order) {
        Discount discount = discountSchedulerService.getCurrentDiscount();
        OrderItem orderItem = orderItemRepository.save(new OrderItem());
        orderItem.setOrder(order);
        orderItem.setItem(basketItem.getItem());
        orderItem.setCount(basketItem.getCount());
        if (discount.getItem() == basketItem.getItem()) {
            double price = Math.round(
                    discount.getValCoefficient() * basketItem.getItem().getPrice() * basketItem.getCount());
            orderItem.setFixedItemPrice(price);
        } else orderItem.setFixedItemPrice(basketItem.getItem().getPrice() * basketItem.getCount());
        return orderItem;
    }


    //@Override
   /* @Transactional
    public SaleItemDto createSaleItem(SaleItemDto saleItemDto){
        return SaleItemMapper.toDto(bucketItemsRepository.save(SaleItemMapper.fromDto(saleItemDto)));
    }

    //@Override
    public SaleItemDto readSaleItem(Long saleItemId){
        return SaleItemMapper.toDto(findSaleItemInRepository(saleItemId));
    }

   //@Override
    @Transactional
    public SaleItemUpdateDto updateSaleItem(Long saleItemId, SaleItemDto saleItemDto){
        BucketItem bucketItem = findSaleItemInRepository(saleItemId);
        Optional.ofNullable(saleItemDto.getItem()).ifPresent(bucketItem::setItem);
        Optional.ofNullable(saleItemDto.getBucket()).ifPresent(bucketItem::setBucket);
        Optional.ofNullable(saleItemDto.getCount()).ifPresent(bucketItem::setCount);
        Optional.ofNullable(saleItemDto.getDiscount()).ifPresent(bucketItem::setDiscount);
        return SaleItemMapper.toUpdateDto(bucketItemsRepository.save(SaleItemMapper.fromDto(saleItemDto)));
    }

    //@Override
    @Transactional
    public void deleteSaleItem(Long saleItemId){
        bucketItemsRepository.delete(findSaleItemInRepository(saleItemId));
    }

    //@Override
    public BucketItem findSaleItemInRepository(Long saleId){
        return bucketItemsRepository.findById(saleId)
                .orElseThrow(()->new CrudException("Cannot SaleItem with id = " + saleId));
    }


    @Override
    @Transactional
    public SaleDto createSale(SaleDto saleDto){
        return SaleMapper.toDto(bucketRepository.save(SaleMapper.fromDto(saleDto)));
    }

    @Override
    public SaleDto readSale(Long saleId){
        return SaleMapper.toDto(findSaleInRepository(saleId));
    }

    @Override
    @Transactional
    public SaleUpdateDto updateSale(Long saleId, SaleDto saleDto){
        Bucket bucket = findSaleInRepository(saleId);
        Optional.ofNullable(saleDto.getCreatedOn()).ifPresent(bucket::setCreatedOn);
        Optional.ofNullable(saleDto.getShoppingList()).ifPresent(bucket::setShoppingList);
        return SaleMapper.toUpdateDto(bucketRepository.save(SaleMapper.fromDto(saleDto)));
    }

    @Override
    @Transactional
    public void deleteSale(Long saleId){
        bucketItemsRepository.delete(findSaleItemInRepository(saleId));
    }

    @Override
    public Bucket findSaleInRepository(Long saleId){
        return bucketRepository.findById(saleId)
                .orElseThrow(()->new CrudException("Cannot SaleItem with id = " + saleId));
    }*/

}
