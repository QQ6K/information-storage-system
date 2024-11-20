package ru.task.miss.servicies;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.task.miss.repositories.SaleProductsRepository;
import ru.task.miss.repositories.SalesRepository;
import ru.task.miss.exceptions.CrudException;
import ru.task.miss.models.Sale;
import ru.task.miss.models.SalesProductStatDto;
import ru.task.miss.models.StatisticData;
import ru.task.miss.repositories.StatisticsRepository;
import ru.task.miss.interfaces.StatisticsService;
import ru.task.miss.dtos.StatisticDataDto;
import ru.task.miss.mappers.StatisticDataMapper;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class StatisticsServiceImpl implements StatisticsService {

    private final SalesRepository salesRepository;

    private final StatisticsRepository statisticsRepository;

    private final SaleProductsRepository saleProductsRepository;

    @Scheduled(fixedDelay = 3600000)
    @Override
    @Transactional
    public void makeStat() {
        log.info("Сделать статистический подсчет Стат");
        LocalDateTime endDateTic = LocalDateTime.now();
        LocalDateTime startDateTic = endDateTic.minusHours(1);
        statCalc(endDateTic, startDateTic);
    }

    @Override
    @Transactional
    public Collection<StatisticDataDto> getStat() {
        Collection<StatisticDataDto> statisticData = statisticsRepository
                .findAll().stream()
                .map(StatisticDataMapper::toStatisticDataDto)
                .collect(Collectors.toCollection(ArrayList::new));
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
                statCalc(endDateTic, startDateTic);
            }
        } else throw new CrudException("Нет продаж. Невозможно посчитать статистику");
    }

    @Override
    public SalesProductStatDto getStatForProduct(Long vendorCode) {
        SalesProductStatDto salesProductStat = new SalesProductStatDto();
        salesProductStat.setTotalCountSales(saleProductsRepository.getSaleProductsCountByVendorCode(vendorCode));
        salesProductStat.setTotalSum(saleProductsRepository.getSaleProductsTotalSumByVendorCode(vendorCode));
        return salesProductStat;
    }


    @Override
    public void statCalc(LocalDateTime endDateTic, LocalDateTime startDateTic) {
        Collection<Sale> sales = salesRepository.getSales(startDateTic, endDateTic);

        // Используем новый метод для генерации dateTimeCode для endDateTic
        int dTc = generateDateTimeCode(endDateTic);
        StatisticData temp = statisticsRepository.findByDateTimeCode(dTc);
        StatisticData statisticData = new StatisticData();

        if (temp != null) {
            statisticData = temp;
        } else {
            statisticData.setDateTimeCode(dTc);
        }

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
        statisticData.setSumWithDiscount(sumWithDiscount);
        statisticData.setAvgSumWithDiscount(
                (statisticData.getCountReceipts() == 0
                        ? 0
                        : sumWithDiscount / statisticData.getCountReceipts()));

        // Генерация dateTimeCode для startDateTic
        int startDateTimeCode = generateDateTimeCode(startDateTic);
        StatisticData lastStatisticData = statisticsRepository
                .findByDateTimeCode(startDateTimeCode);

        statisticData.setIncrease(
                lastStatisticData != null
                        ? statisticData.getAvgSumWithDiscount() - lastStatisticData.getAvgSumWithDiscount()
                        : statisticData.getAvgSumWithDiscount());

        statisticData.setStarting(startDateTic);
        statisticData.setEnding(endDateTic);
        statisticData.setNewest(true);

        statisticsRepository.save(statisticData);
    }

    // Новый метод для генерации dateTimeCode
    private int generateDateTimeCode(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHH");
        String formattedDate = dateTime.format(formatter);
        return Integer.parseInt(formattedDate);
    }


}
