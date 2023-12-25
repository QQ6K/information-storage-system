package ru.task.iss.statistics.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.cart.repository.SalesRepository;
import ru.task.iss.models.Sale;
import ru.task.iss.statistics.services.StatisticsService;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final SalesRepository salesRepository;

    @Override
    public void getRecalculate(){
        LocalDateTime startDate = salesRepository.getStartDate();
        LocalDateTime endDate = salesRepository.getEndDate();
        Collection<Sale> sales = salesRepository.getSales(startDate, endDate);

        return;
    }

}
