package ru.task.iss.items.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    private Long vendorCode;

    private String name;

    private Integer price;

    private Long amount;
}
