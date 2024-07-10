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
                statisticData.getSumWithoutDiscounts(),
                statisticData.getAvgSumWithoutDiscounts(),
                statisticData.getDiscountSum(),
                statisticData.getSumWithDiscount(),
                statisticData.getAvgSumWithDiscount(),
                statisticData.getIncrease()
        );
    }
}