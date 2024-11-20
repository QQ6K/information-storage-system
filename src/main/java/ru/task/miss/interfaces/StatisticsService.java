package ru.task.miss.interfaces;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import ru.task.miss.models.SalesProductStatDto;
import ru.task.miss.dtos.StatisticDataDto;

import java.time.LocalDateTime;
import java.util.Collection;

public interface StatisticsService {
    @Scheduled(fixedDelay = 600000)
    @Transactional
    void makeStat();

    @Transactional
    Collection<StatisticDataDto> getStat();

    void getRecalculate();


    SalesProductStatDto getStatForProduct(Long vendorCode);

    StatisticDataDto statCalc(LocalDateTime endDateTic, LocalDateTime startDateTic);
}
