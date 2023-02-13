package ru.task.iss.sales.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.task.iss.models.Sale;

import java.time.LocalDateTime;

@Repository
public interface SalesRepository extends JpaRepository<Sale,Long> {
    @Query("select distinct s from Sale s where s.createdOn >= :starting and s.createdOn <= :ending")
    //можно через id скидки, но не очень нравится идея
    public Long getCashReceiptFromHourCount(LocalDateTime starting, LocalDateTime ending);

    //public LocalDateTime findFirstByCreatedOnOrderByCreatedOnDesc();

   // public LocalDateTime findFirstByCreatedOnOrderByCreatedOnAsc();
    @Query("SELECT sum(s.item.price) FROM SaleItem s where s.sale.createdOn between :starting and :ending")
    public LocalDateTime getFullSum(LocalDateTime starting, LocalDateTime ending);

}
