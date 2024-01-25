package ru.task.iss.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "statistics")
//билдер
public class StatisticData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "datetime_code")
    private int dateTimeCode; //дата+час для статистики и получения
    private LocalDateTime starting;
    private LocalDateTime ending;
    @Column(name = "count_receipts")
    private Integer countReceipts; // количество чеков
    @Column(name = "sum_without_discounts")
    private Integer sumWithoutDiscounts; //общая стоимость чеков
    @Column(name = "avg_sum_without_discounts")
    private Integer avgSumWithoutDiscounts; //стоимость среднего чека
    @Column(name = "discount_sum")
    private Integer discountSum; //сумма скидок
    @Column(name = "sum_with_discounts")
    private Integer sumWithDiscount; //общая стоимость с учетом скидок
    @Column(name = "avg_sum_with_discounts")
    private Integer avgSumWithDiscount; //стоимость среднего чека с учетом скидок
    @Column(name = "increase_receipts")
    private Integer increase; // прирост среднего чека к предыдущему часу

    @Column(name = "newest")
    private Boolean newest;
}