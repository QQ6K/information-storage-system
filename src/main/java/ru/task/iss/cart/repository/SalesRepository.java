package ru.task.iss.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.task.iss.models.Sale;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface SalesRepository extends JpaRepository<Sale,Long> {

    @Query(value = "SELECT MAX(code) FROM SALES", nativeQuery = true)
    Long findMaximum();

    @Query(value = "SELECT MIN(created_on) FROM SALES s GROUP BY s.id limit 1", nativeQuery = true)
    LocalDateTime getStartDate();

    @Query(value = "SELECT MAX(created_on) FROM SALES s GROUP BY s.id limit 1", nativeQuery = true)
    LocalDateTime getEndDate();

    @Query(value = "SELECT Sale FROM SALES s WHERE s.createdOn BETWEEN :startDate AND :endDate", nativeQuery = true)
    Collection<Sale> getSales(LocalDateTime startDate, LocalDateTime endDate);
}
