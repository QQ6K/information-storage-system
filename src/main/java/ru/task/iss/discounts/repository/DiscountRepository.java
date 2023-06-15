package ru.task.iss.discounts.repository;
/*
import lombok.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.task.iss.models.Discount;

public interface DiscountRepository extends JpaRepository<Discount,Long> {
    public Discount findFirstByOrderByIdDesc();

    @Query(value = "SELECT TOP 1 d.ID FROM Discounts d ORDER BY d.id DESC", nativeQuery = true)
    public Discount findPastDiscount();
}
*/