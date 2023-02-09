package ru.task.iss.statistics.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.discounts.scheduler.DiscountSchedulerService;
import ru.task.iss.sales.repositories.SalesRepository;
import ru.task.iss.statistics.services.StatisticsService;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final SalesRepository salesRepository;

    private final DiscountSchedulerService discountSchedulerService;

    public LocalDateTime getEarlyTimeSale(){
        return salesRepository.findFirstByCreatedOnOrderByCreatedOnDesc();
    }

    public LocalDateTime getLateDateTime(){
        return salesRepository.findFirstByCreatedOnOrderByCreatedOnAsc();
    }

    public Long getCashReceiptCountHour(){
        return salesRepository.getCashReceiptFromHourCount(
                discountSchedulerService.getPastDiscount().getStart(),
                discountSchedulerService.getPastDiscount().getEnd());
    }


}
