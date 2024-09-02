package ru.task.iss.discounts.services.dto;

import ru.task.iss.common.DateTimeFormatterCustom;
import ru.task.iss.models.Discount;

public class DiscountMapper {

    public static DiscountDto toDiscountDto(Discount discount) {
        return new DiscountDto(
                discount.getDiscountCode(),
                discount.getCoefficient(),
                discount.getProductVendorCode(),
                discount.getName(),
                DateTimeFormatterCustom.formatLocalDateTime(discount.getStarting()),
                DateTimeFormatterCustom.formatLocalDateTime(discount.getEnding()));
    }

}
