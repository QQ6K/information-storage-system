package ru.task.iss.discounts.repository;

import lombok.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.task.iss.models.Discount;

public interface DiscountRepository extends JpaRepository<Discount,Long> {
    public Discount findFirstByOrderByIdDesc();

    @Query("SELECT d FROM  Discount d WHERE d.id = :id ")
    public Discount findDiscount(Long id);

    @Query(value = "SELECT TOP 2 d FROM Discount d ORDER BY d.id DESC LIMIT 1", nativeQuery = true)
    public Discount findPastDiscount();
}
