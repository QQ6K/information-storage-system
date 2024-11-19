package ru.task.miss.mappers;

import ru.task.miss.common.DateTimeFormatterCustom;
import ru.task.miss.dtos.DiscountDto;
import ru.task.miss.models.Discount;

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
