package ru.task.iss.statistics.services.impl;
/*
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.discounts.scheduler.DiscountSchedulerService;
import ru.task.iss.models.StatisticData;
import ru.task.iss.sales.repositories.BasketRepository;
import ru.task.iss.statistics.repositories.StatisticsMemoryRepository;
import ru.task.iss.statistics.services.StatisticsService;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final BasketRepository basketRepository;

    private final DiscountSchedulerService discountSchedulerService;

    private final StatisticsMemoryRepository statisticsMemoryRepository;

    /*public LocalDateTime getEarlyTimeSale(){
        return salesRepository.findFirstByCreatedOnOrderByCreatedOnDesc();
    }*/

    /*public LocalDateTime getLateDateTime(){
        return salesRepository.findFirstByCreatedOnOrderByCreatedOnAsc();
    }*/

   /* public Long getCashReceiptCountHour(){
        return bucketRepository.getCashReceiptFromHourCount(
                discountSchedulerService.getPastDiscount().getStarting(),
                discountSchedulerService.getPastDiscount().getEnding());
    }

    public StatisticData calculateStatistics(){

        return null;
    }


}*/
