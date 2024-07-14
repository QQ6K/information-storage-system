package ru.task.iss.cart.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.task.iss.models.SaleItem;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface SaleItemsRepository extends JpaRepository<SaleItem, Long> {

    @Query(value = "SELECT MAX(sale_code) FROM sale_items", nativeQuery = true)
    Long findMaximum();

    @Query(value = "SELECT MIN(created_on) FROM sale_items s GROUP BY s.id limit 1", nativeQuery = true)
    LocalDateTime getStartDate();

    @Query(value = "SELECT MAX(created_on) FROM sale_items s GROUP BY s.id limit 1", nativeQuery = true)
    LocalDateTime getEndDate();

    @Query(value = "SELECT * FROM sale_items s WHERE s.created_on BETWEEN :startDate AND :endDate", nativeQuery = true)
    Collection<SaleItem> getSales(LocalDateTime startDate, LocalDateTime endDate);

    /*@Query(value = "SELECT * FROM sale_items s WHERE s.sale_code = :saleCode", nativeQuery = true)
    Collection<Sale> getSalesByCode(int saleCode);*/

    @Query(value = "SELECT COUNT(*) as total_count FROM sale_items s WHERE s.vendor_code = :vendorCode", nativeQuery = true)
    int getSaleItemsCountByVendorCode(Long vendorCode);

    @Query(value = "SELECT SUM(total_price) as total_price FROM sale_items s WHERE s.vendor_code = :vendorCode", nativeQuery = true)
    Long getSaleItemsTotalSumByVendorCode(Long vendorCode);

    Page<SaleItem> findByVendorCode(Long vendorCode, Pageable pageable);
}
