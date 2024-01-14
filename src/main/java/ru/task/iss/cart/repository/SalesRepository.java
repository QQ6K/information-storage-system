package ru.task.iss.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.task.iss.models.Sale;
import ru.task.iss.models.SaleItem;

@Repository
public interface SalesRepository extends JpaRepository<Sale,Long> {
}
