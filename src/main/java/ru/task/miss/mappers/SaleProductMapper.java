package ru.task.miss.mappers;

import ru.task.miss.common.DateTimeFormatterCustom;
import ru.task.miss.dtos.SaleProductDto;
import ru.task.miss.models.SaleProduct;

public class SaleProductMapper {
    public static SaleProductDto toSaleProductDto(SaleProduct saleProduct) {
        return new SaleProductDto(saleProduct.getSaleCode(),
                saleProduct.getVendorCode(),
                saleProduct.getName(),
                saleProduct.getPrice(),
                saleProduct.getAmount(),
                saleProduct.getDiscount(),
                saleProduct.getFinalPrice(),
                saleProduct.getTotalPrice(),
                DateTimeFormatterCustom.formatLocalDateTime(saleProduct.getCreatedOn()));
    }
}