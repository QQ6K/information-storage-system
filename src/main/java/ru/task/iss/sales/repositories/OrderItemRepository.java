package ru.task.iss.sales.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.task.iss.models.saleItem;

public interface OrderItemRepository extends JpaRepository<saleItem,Long> {
}
