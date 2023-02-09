package ru.task.iss.sales.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.task.iss.models.SaleItem;
@Repository
public interface SaleItemsRepository extends JpaRepository<SaleItem,Long> {
}
