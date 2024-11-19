package ru.task.miss.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticDataDto {
    private String starting;
    private String ending;
    private Long countReceipts; // количество чеков
    private Long sumWithoutDiscounts; //общая стоимость чеков
    private Long avgSumWithoutDiscounts; //стоимость среднего чека
    private Long discountSum; //сумма скидок
    private Long sumWithDiscount; //общая стоимость с учетом скидок
    private Long avgSumWithDiscount; //стоимость среднего чека с учетом скидок
    private Long increase; // прирост среднего чека к предыдущему часу
}