package ru.task.iss.sales.services.dtos;

import ru.task.iss.models.SaleItem;

public class SaleItemMapper {

    public static SaleItemDto toDto(SaleItem itemSale) {
        return new SaleItemDto(
                itemSale.getId(),
                itemSale.getItem(),
                itemSale.getSale(),
                itemSale.getCount(),
                itemSale.getDiscount());
    }

    public static SaleItem fromDto(SaleItemDto saleItemDto) {
        return new SaleItem(
                saleItemDto.getId(),
                saleItemDto.getItem(),
                saleItemDto.getSale(),
                saleItemDto.getCount(),
                saleItemDto.getDiscount());
    }

    public static SaleItemUpdateDto toUpdateDto(SaleItem saleItem) {
        return new SaleItemUpdateDto(
                saleItem.getId(),
                saleItem.getItem(),
                saleItem.getSale(),
                saleItem.getCount(),
                saleItem.getDiscount());
    }

}
