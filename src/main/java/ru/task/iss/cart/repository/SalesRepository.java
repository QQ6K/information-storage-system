package ru.task.iss.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.task.iss.models.Sale;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface SalesRepository extends JpaRepository<Sale, Long> {

    @Query(value = "SELECT MIN(created_on) FROM sales s", nativeQuery = true)
    LocalDateTime getStartDate();

    @Query(value = "SELECT MAX(created_on) FROM sales s", nativeQuery = true)
    LocalDateTime getEndDate();

    @Query(value = "SELECT * FROM sales s WHERE s.created_on BETWEEN :startDate AND :endDate", nativeQuery = true)
    Collection<Sale> getSales(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "SELECT * FROM sales s WHERE s.sale_code = :salesCode", nativeQuery = true)
    Collection<Sale> getSalesByCode(int salesCode);
}
