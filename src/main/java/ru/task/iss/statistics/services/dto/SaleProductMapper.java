package ru.task.iss.statistics.services.dto;

import ru.task.iss.common.DateTimeFormatterCustom;
import ru.task.iss.models.SaleProduct;

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