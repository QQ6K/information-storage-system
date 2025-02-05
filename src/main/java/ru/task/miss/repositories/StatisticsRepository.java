package ru.task.miss.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.task.miss.models.StatisticData;

public interface StatisticsRepository extends JpaRepository<StatisticData, Long> {

    StatisticData findFirstByOrderByDateTimeCodeDesc();

    StatisticData findByDateTimeCode(int DateTimeCode);

}
