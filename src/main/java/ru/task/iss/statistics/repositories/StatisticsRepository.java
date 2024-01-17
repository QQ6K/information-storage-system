package ru.task.iss.statistics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.task.iss.models.StatisticData;

import java.util.Collection;

public interface StatisticsRepository extends JpaRepository<StatisticData, Long> {

    StatisticData findFirstByOrderByDateTimeCodeDesc();

    StatisticData findByDateTimeCode(int DateTimeCode);

}
