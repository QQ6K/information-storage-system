package ru.task.iss.items.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    //@Size(min = 1, message = "Артикул не может быть пустым")
    @Min(value = 0L, message = "Артикул не может быть отрицательным числом")
    //@Pattern(regexp="^(0|[1-9][0-9]*)$",message = "Артикул должен быть числом")
    private Long vendorCode;
    private String name;
    @Min(value = 0L, message = "Цена не может быть отрицательной")
    private Double price;
    @Min(value = 0L, message = "Количество не может быть отрицательным")
    private Long amount;

    public ItemDto(Long vendorCode, String name, Long price, Long amount) {
        this.name = name;
        this.amount = amount;
        this.vendorCode = vendorCode;
        this.price = Double.valueOf(price);

    }
}
