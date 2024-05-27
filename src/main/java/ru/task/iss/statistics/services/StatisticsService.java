package ru.task.iss.statistics.services;

import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.models.SalesItemStatDto;
import ru.task.iss.models.StatisticData;

import java.util.Collection;

public interface StatisticsService {
    @Transactional
    Collection<StatisticData> getStat();

    void getRecalculate();

    SalesItemStatDto getStatForItem(Long vendorCode);
}
