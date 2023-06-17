package ru.task.iss.sales.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.task.iss.models.Sale;

public interface OrderRepository extends JpaRepository<Sale,Long> {
}
