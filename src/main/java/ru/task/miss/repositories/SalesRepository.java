package ru.task.miss.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.task.miss.dtos.StatisticDataDto;
import ru.task.miss.models.Sale;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface SalesRepository extends JpaRepository<Sale, Long> {

    @Query(value = "SELECT MIN(created_on) FROM sales s", nativeQuery = true)
    LocalDateTime getStartDate();

    @Query(value = "SELECT MAX(created_on) FROM sales s", nativeQuery = true)
    LocalDateTime getEndDate();

    @Query(value = "SELECT " +
            "COUNT(s.id) AS countReceipts, " +
            "SUM(s.price) AS sumWithoutDiscounts, " +
            "SUM(s.discount_sum) AS discountSum, " +
            "SUM(s.final_price) AS sumWithDiscount, " +
            "AVG(s.price) AS avgSumWithoutDiscounts, " +
            "AVG(s.final_price) AS avgSumWithDiscount, " +
            "AVG(s.final_price) - " +
            "(SELECT AVG(s2.final_price) FROM sales s2 WHERE s2.created_on BETWEEN " +
            "  (TO_TIMESTAMP(:endDate, 'YYYY-MM-DD HH24:MI') - INTERVAL '2 hour') AND " + // Преобразование endDate и вычисление предыдущего часа
            "  (TO_TIMESTAMP(:endDate, 'YYYY-MM-DD HH24:MI') - INTERVAL '1 hour') " + // Преобразование endDate и вычисление предыдущего часа
            ") AS increase, " +
            "TO_CHAR(s.created_on, 'YYYY-MM-DD HH24:MI') AS starting, " +  // Преобразование в строку
            "TO_CHAR(s.created_on, 'YYYY-MM-DD HH24:MI') AS ending " +    // Преобразование в строку
            "FROM sales s " +
            "WHERE s.created_on BETWEEN (TO_TIMESTAMP(:endDate, 'YYYY-MM-DD HH24:MI') - INTERVAL '1 hour') AND " +
            "  TO_TIMESTAMP(:endDate, 'YYYY-MM-DD HH24:MI') " +
            "GROUP BY TO_CHAR(s.created_on, 'YYYY-MM-DD HH24:MI')", // Группируем по времени
            nativeQuery = true)
    StatisticDataDto getStatisticData(LocalDateTime endDate);



    @Query(value = "SELECT * FROM sales s WHERE s.sale_code = :salesCode", nativeQuery = true)
    Collection<Sale> getSalesByCode(int salesCode);
}
