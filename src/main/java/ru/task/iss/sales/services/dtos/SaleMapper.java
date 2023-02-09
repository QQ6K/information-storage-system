package ru.task.iss.sales.services.dtos;

import ru.task.iss.models.Sale;

public class SaleMapper {

    public static SaleDto toDto(Sale sale) {
        return new SaleDto(
                sale.getId(),
                sale.getCreatedOn(),
                sale.getShoppingList());
    }

    public static Sale fromDto(SaleDto saleDto) {
        return new Sale(
                saleDto.getId(),
                saleDto.getCreatedOn(),
                saleDto.getShoppingList());
    }

    public static Sale fromUpdateDto(SaleUpdateDto saleUpdateDto) {
        return new Sale(
                saleUpdateDto.getId(),
                saleUpdateDto.getCreatedOn(),
                saleUpdateDto.getShoppingList());
    }

    public static SaleUpdateDto toUpdateDto(Sale sale) {
        return new SaleUpdateDto(
                sale.getId(),
                sale.getCreatedOn(),
                sale.getShoppingList());
    }
}
