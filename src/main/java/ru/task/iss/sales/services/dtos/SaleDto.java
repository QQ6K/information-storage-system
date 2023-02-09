package ru.task.iss.sales.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.task.iss.models.SaleItem;

import java.time.LocalDateTime;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDto {
    Long id;
    LocalDateTime createdOn;
    List<SaleItem> shoppingList;
}
