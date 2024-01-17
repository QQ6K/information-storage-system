package ru.task.iss.statistics.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.util.Precision;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.cart.repository.SalesRepository;
import ru.task.iss.models.Sale;
import ru.task.iss.models.StatisticData;
import ru.task.iss.statistics.repositories.StatisticsRepository;
import ru.task.iss.statistics.services.StatisticsService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class StatisticsServiceImpl implements StatisticsService {

    private final SalesRepository salesRepository;

    private final StatisticsRepository statisticsRepository;


    @Override
    @Transactional
    public Collection<StatisticData> getStat(){
        Collection<StatisticData> statisticData= statisticsRepository.findAll();
        log.info("Стат");
        return statisticData;
    }

    @Override
    @Transactional
    public void getRecalculate(){
        LocalDateTime startDate = salesRepository.getStartDate();
        LocalDateTime endDate = salesRepository.getEndDate();
        Duration duration = Duration.between(startDate, endDate);
        long timeIntervalCount = (duration.toHours());
        log.info("timeIntervalCount {}", timeIntervalCount);

        for (int tic = 0; tic <= timeIntervalCount; tic++) {
            LocalDateTime startDateTic = startDate.plusHours(tic);
            LocalDateTime endDateTic = startDate.plusHours(tic+1);
            Collection<Sale> sales = salesRepository.getSales(startDateTic, endDateTic);

            StatisticData statisticData = new StatisticData();
            statisticData.setCountReceipts(sales.size());

            double sumWithoutDiscounts = 0.0;
            double discountSum = 0.0;
            double sumWithDiscount = 0.0;

            for (Sale sale : sales) {
                sumWithoutDiscounts += sale.getPrice();
                discountSum += sale.getDiscountSum();
                sumWithDiscount += sale.getFinalPrice();
            }

            statisticData.setSumWithoutDiscounts(Precision.round(sumWithoutDiscounts, 2));
            statisticData.setAvgSumWithoutDiscounts(
                    statisticData.getCountReceipts() == 0
                            ? 0
                            : Precision.round(sumWithoutDiscounts / statisticData.getCountReceipts() , 2));
            statisticData.setDiscountSum(Precision.round(discountSum,2));
            statisticData.setSumWithDiscount(Precision.round(sumWithDiscount,2));
            statisticData.setAvgSumWithDiscount(
                    (statisticData.getCountReceipts() == 0
                            ? 0
                            : Precision.round(sumWithDiscount /statisticData.getCountReceipts(),2)));

            StringBuilder dateTimeCode = new StringBuilder(10);
            dateTimeCode
                    .append(endDateTic.getYear())
                    .append(endDateTic.getMonthValue()<10 ? "0"+endDateTic.getMonthValue() : endDateTic.getMonth())
                    .append(endDateTic.getDayOfMonth()<10 ? "0"+endDateTic.getDayOfMonth() : endDateTic.getDayOfMonth())
                    .append(endDateTic.getHour()<10 ? "0"+endDateTic.getHour() : endDateTic.getHour());

            statisticData.setDateTimeCode(Integer.parseInt(String.valueOf(dateTimeCode)));

            dateTimeCode.delete(0, dateTimeCode.length());
            dateTimeCode
                    .append(startDateTic.getYear())
                    .append(startDateTic.getMonthValue()<10 ? "0"+startDateTic.getMonthValue() : startDateTic.getMonthValue())
                    .append(startDateTic.getDayOfMonth()<10 ? "0"+startDateTic.getDayOfMonth() : startDateTic.getDayOfMonth())
                    .append(startDateTic.getHour()<10 ? "0"+startDateTic.getHour() : startDateTic.getHour());

            StatisticData lastStatisticData = statisticsRepository
                    .findByDateTimeCode(Integer.parseInt(String.valueOf(dateTimeCode)));
            statisticData.setIncrease(
                    lastStatisticData != null
                            ? Precision.round(statisticData.getAvgSumWithDiscount() - lastStatisticData.getAvgSumWithDiscount(), 2)
                            : Precision.round(statisticData.getAvgSumWithDiscount(),2));


            statisticData.setStarting(startDateTic);
            statisticData.setEnding(endDateTic);
            statisticsRepository.save(statisticData);
        }
    }



}
