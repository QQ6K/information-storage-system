package ru.task.iss.sales.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.task.iss.models.Discount;
import ru.task.iss.models.Item;
import ru.task.iss.models.Sale;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleItemUpdateDto {
    Long id;
    Item item;
    Sale sale;
    int count;
    Discount discount;
}
