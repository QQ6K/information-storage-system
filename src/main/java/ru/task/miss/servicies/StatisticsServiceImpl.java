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
    public StatisticDataDto statCalc(LocalDateTime endDateTic, LocalDateTime startDateTic) {
        // Для вычисления прироста передаем два промежутка времени: текущий и предыдущий час
        LocalDateTime previousStartDate = startDateTic.minusHours(1);  // Предыдущий час
        LocalDateTime previousEndDate = endDateTic.minusHours(1);  // Предыдущий час
         return salesRepository.getStatisticData(startDateTic, endDateTic);
    }


    // Новый метод для генерации dateTimeCode
    private int generateDateTimeCode(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHH");
        String formattedDate = dateTime.format(formatter);
        return Integer.parseInt(formattedDate);
    }


}
