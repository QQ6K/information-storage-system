package ru.task.iss.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticDataDto {
    private Long countReceipts;
    private Double fullSum; //общая стоимость чеков
    private Double avgFull; //стоимость среднего чека
    private Double discountSum; //сумма скидок
    private Double sumWithDiscount; //общая стоимость с учетом скидок
    private Double avgWithDiscount; //стоимость среднего чека с учетом скидок
    private Double increase; // прирост среднего чека к предыдущему часу



}
