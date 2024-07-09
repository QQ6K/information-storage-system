package ru.task.iss.statistics.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.cart.repository.SaleItemsRepository;
import ru.task.iss.cart.repository.SalesRepository;
import ru.task.iss.common.DateTimeFormatterCustom;
import ru.task.iss.exceptions.CrudException;
import ru.task.iss.models.Sale;
import ru.task.iss.models.SalesItemStatDto;
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
@EnableScheduling
public class StatisticsServiceImpl implements StatisticsService {

    private final SalesRepository salesRepository;

    private final StatisticsRepository statisticsRepository;

    private final SaleItemsRepository saleItemsRepository;

    @Scheduled(fixedDelay = 3600000)
    @Override
    @Transactional
    public void makeStat() {
        log.info("Сделать статистический подсчет Стат");
        LocalDateTime endDateTic = LocalDateTime.now();
        LocalDateTime startDateTic = endDateTic.minusHours(1);
        statCalc(endDateTic,startDateTic);
    }

    @Override
    @Transactional
    public Collection<StatisticData> getStat() {
        Collection<StatisticData> statisticData = statisticsRepository.findAll();
        log.info("Стат");
        return statisticData;
    }

    @Override
    @Transactional
    public void getRecalculate() {
        LocalDateTime startDate = salesRepository.getStartDate();
        LocalDateTime endDate = salesRepository.getEndDate();
        if (endDate != null) {
            Duration duration = Duration.between(startDate, endDate);
            long timeIntervalCount = (duration.toHours());
            log.info("timeIntervalCount {}", timeIntervalCount);

            for (int tic = 0; tic <= timeIntervalCount; tic++) {
                LocalDateTime startDateTic = startDate.plusHours(tic);
                LocalDateTime endDateTic = startDate.plusHours(tic + 1);
                statCalc(endDateTic,startDateTic);
            }
        } else throw new CrudException("Нет продаж. Невозможно посчитать статистику");
    }

    @Override
    public SalesItemStatDto getStatForItem(Long vendorCode) {
        SalesItemStatDto salesItemStat = new SalesItemStatDto();
        salesItemStat.setTotalCountSales(saleItemsRepository.getSaleItemsCountByVendorCode(vendorCode));
        salesItemStat.setTotalSum(saleItemsRepository.getSaleItemsTotalSumByVendorCode(vendorCode));
        return salesItemStat;
    }


    @Override
    public void statCalc(LocalDateTime endDateTic, LocalDateTime startDateTic) {

        Collection<Sale> sales = salesRepository.getSales(startDateTic, endDateTic);

        StatisticData statisticData = new StatisticData();
        statisticData.setCountReceipts((long) sales.size());

        Long sumWithoutDiscounts = 0L;
        Long discountSum = 0L;
        Long sumWithDiscount = 0L;

        for (Sale sale : sales) {
            sumWithoutDiscounts += sale.getPrice();
            discountSum += sale.getDiscountSum();
            sumWithDiscount += sale.getFinalPrice();
        }

        statisticData.setSumWithoutDiscounts(sumWithoutDiscounts);
        statisticData.setAvgSumWithoutDiscounts(
                statisticData.getCountReceipts() == 0
                        ? 0
                        : sumWithoutDiscounts / statisticData.getCountReceipts());
        statisticData.setDiscountSum(discountSum);
        statisticData.setSumWithDiscount((sumWithDiscount));
        statisticData.setAvgSumWithDiscount(
                (statisticData.getCountReceipts() == 0
                        ? 0
                        : sumWithDiscount / statisticData.getCountReceipts()));

        StringBuilder dateTimeCode = new StringBuilder(10);
        dateTimeCode
                .append(endDateTic.getYear())
                .append(endDateTic.getMonthValue() < 10 ? "0" + endDateTic.getMonthValue() : endDateTic.getMonth())
                .append(endDateTic.getDayOfMonth() < 10 ? "0" + endDateTic.getDayOfMonth() : endDateTic.getDayOfMonth())
                .append(endDateTic.getHour() < 10 ? "0" + endDateTic.getHour() : endDateTic.getHour());

        statisticData.setDateTimeCode(Integer.parseInt(String.valueOf(dateTimeCode)));
        dateTimeCode.delete(0, dateTimeCode.length());

        dateTimeCode
                .append(startDateTic.getYear())
                .append(startDateTic.getMonthValue() < 10 ? "0" + startDateTic.getMonthValue() : startDateTic.getMonthValue())
                .append(startDateTic.getDayOfMonth() < 10 ? "0" + startDateTic.getDayOfMonth() : startDateTic.getDayOfMonth())
                .append(startDateTic.getHour() < 10 ? "0" + startDateTic.getHour() : startDateTic.getHour());

        StatisticData lastStatisticData = statisticsRepository
                .findByDateTimeCode(Integer.parseInt(String.valueOf(dateTimeCode)));
        statisticData.setIncrease(
                lastStatisticData != null
                        ? statisticData.getAvgSumWithDiscount() - lastStatisticData.getAvgSumWithDiscount()
                        : statisticData.getAvgSumWithDiscount());

        statisticData.setStarting(DateTimeFormatterCustom.formatLocalDateTime(startDateTic));
        statisticData.setEnding(DateTimeFormatterCustom.formatLocalDateTime(endDateTic));
        statisticsRepository.save(statisticData);
    }

}
