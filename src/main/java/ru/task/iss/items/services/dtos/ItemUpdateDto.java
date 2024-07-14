package ru.task.iss.items.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemUpdateDto {

    private Long id;

    @Min(value = 0L, message = "Артикул положительное число")
    private Long vendorCode;

    private String name;

    @Min(value = 0L, message = "Цена не может быть отрицательной")
    private double price;

    @Min(value = 0L, message = "Количество не может быть отрицательным")
    private Long amount;
}