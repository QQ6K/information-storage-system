package ru.task.iss.statistics.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.cart.repository.SaleItemsRepository;
import ru.task.iss.cart.repository.SalesRepository;
import ru.task.iss.models.Sale;
import ru.task.iss.models.SaleItem;
import ru.task.iss.models.StatisticData;
import ru.task.iss.statistics.repositories.StatisticsRepository;
import ru.task.iss.statistics.services.StatisticsService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final SalesRepository salesRepository;

    private final StatisticsRepository statisticsRepository;

    @Override
    public void getRecalculate(){
        LocalDateTime startDate = salesRepository.getStartDate();
        LocalDateTime endDate = salesRepository.getEndDate();
        Duration duration = Duration.between(startDate, endDate);
        long timeIntervalCount = (duration.toHours());

        for (int tic = 0; tic <= timeIntervalCount; tic++) {
            Collection<Sale> sales = salesRepository.getSales(startDate, endDate);

            StatisticData statisticData = new StatisticData();
            Set set = new HashSet();
            for (Sale sale : sales) {
                set.add(sale);
            }
            statisticData.setCountReceipts(set.size());


            Double sumWithoutDiscounts = 0.0;
            Double discountSum = 0.0;
            Double sumWithDiscount = 0.0;

            for (Sale sale : sales) {
                sumWithoutDiscounts += sale.getPrice();
                discountSum += sale.getDiscountSum();
                sumWithDiscount += sale.getFinalPrice();
            }

            statisticData.setSumWithoutDiscounts(sumWithoutDiscounts);
            statisticData.setAvgSumWithoutDiscounts(sumWithoutDiscounts / statisticData.getCountReceipts());
            statisticData.setDiscountSum(discountSum);
            statisticData.setSumWithDiscount(sumWithDiscount);
            statisticData.setAvgWithDiscount(sumWithDiscount / statisticData.getCountReceipts());

            StatisticData lastStatisticData = statisticsRepository.findFirstByOrderByDateTimeCodeDesc();
            statisticData.setIncrease(
                    lastStatisticData != null
                            ? lastStatisticData.getIncrease() - statisticData.getAvgWithDiscount()
                            : 0);

            LocalDateTime dateTime = LocalDateTime.now();
            String dateTimeCode = String.valueOf(dateTime.getYear() +
                    dateTime.getMonthValue() +
                    dateTime.getDayOfMonth() +
                    dateTime.getHour());
            statisticData.setDateTimeCode(Integer.valueOf(dateTimeCode));

            statisticsRepository.save(statisticData);
        }
        return;
    }



}
