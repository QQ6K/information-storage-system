package ru.task.iss.statistics.services.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleItemDto{
    private Long saleCode;
    private Long vendorCode;
    private String name;
    private Long price;
    private Long amount;
    private Long discount;
    private Long finalPrice;
    private Long totalPrice;
    private String createdOn;
}