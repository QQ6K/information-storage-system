package ru.task.iss.statistics.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.models.SalesItemStatDto;
import ru.task.iss.models.StatisticData;

import java.time.LocalDateTime;
import java.util.Collection;

public interface StatisticsService {
    @Scheduled(fixedDelay = 600000)
    @Transactional
    void makeStat();

    @Transactional
    Collection<StatisticData> getStat();

    void getRecalculate();

    SalesItemStatDto getStatForItem(Long vendorCode);


    void statCalc(LocalDateTime endDateTic, LocalDateTime startDateTic);
}
