package ru.task.iss.statistics.services.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.task.iss.common.DateTimeFormatterCustom;
import ru.task.iss.models.StatisticData;

public class StatisticDataMapper {


    public static StatisticDataDto toStatisticDataDto(StatisticData statisticData) {
        return new StatisticDataDto(
                DateTimeFormatterCustom.formatLocalDateTime(statisticData.getStarting()),
                DateTimeFormatterCustom.formatLocalDateTime(statisticData.getEnding()),
                statisticData.getCountReceipts(),
                statisticData.getSumWithoutDiscounts()/100,
                statisticData.getAvgSumWithoutDiscounts()/100,
                statisticData.getDiscountSum()/100,
                statisticData.getSumWithDiscount()/100,
                statisticData.getAvgSumWithDiscount()/100,
                statisticData.getIncrease()/100
        );
    }
}