package ru.task.iss.statistics.services.dto;

import ru.task.iss.common.DateTimeFormatterCustom;
import ru.task.iss.models.SaleItem;

public class SaleItemMapper {
    public static SaleItemDto toSaleItemDto(SaleItem saleItem) {
        return new SaleItemDto(saleItem.getSaleCode(),
                saleItem.getVendorCode(),
                saleItem.getName(),
                saleItem.getPrice(),
                saleItem.getAmount(),
                saleItem.getDiscount(),
                saleItem.getFinalPrice(),
                saleItem.getTotalPrice(),
                DateTimeFormatterCustom.formatLocalDateTime(saleItem.getCreatedOn()));
    }
}