package ru.task.iss.statistics.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.discounts.scheduler.DiscountSchedulerService;
import ru.task.iss.models.StatisticData;
import ru.task.iss.sales.repositories.SalesRepository;
import ru.task.iss.statistics.repositories.StatisticsMemoryRepository;
import ru.task.iss.statistics.services.StatisticsService;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final SalesRepository salesRepository;

    private final DiscountSchedulerService discountSchedulerService;

    private final StatisticsMemoryRepository statisticsMemoryRepository;

    /*public LocalDateTime getEarlyTimeSale(){
        return salesRepository.findFirstByCreatedOnOrderByCreatedOnDesc();
    }*/

    /*public LocalDateTime getLateDateTime(){
        return salesRepository.findFirstByCreatedOnOrderByCreatedOnAsc();
    }*/

    public Long getCashReceiptCountHour(){
        return salesRepository.getCashReceiptFromHourCount(
                discountSchedulerService.getPastDiscount().getStarting(),
                discountSchedulerService.getPastDiscount().getEnding());
    }

    public StatisticData calculateStatistics(){

        return null;
    }


}
