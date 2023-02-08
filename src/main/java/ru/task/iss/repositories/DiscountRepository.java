package ru.task.iss.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.task.iss.models.Discount;

public interface DiscountRepository extends JpaRepository<Discount,Long> {
    public Discount findFirstByOrderByIdDesc();

}
