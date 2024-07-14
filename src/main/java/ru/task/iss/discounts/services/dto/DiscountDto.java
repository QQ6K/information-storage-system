package ru.task.iss.discounts.services.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDto {
    private Long discountCode;
    private Integer coefficient;
    private Long itemVendorCode;
    private String name;
    private String starting;
    private String ending;
}