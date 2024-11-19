package ru.task.miss.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.task.miss.models.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Long> {

    Discount findFirstByOrderByIdDesc();

    @Query(value = "SELECT TOP 1 d.id FROM Discounts d ORDER BY d.id DESC", nativeQuery = true)
    Discount findPastDiscount();

    //Discount findDiscountByIdOrderByIdDesc();

}
