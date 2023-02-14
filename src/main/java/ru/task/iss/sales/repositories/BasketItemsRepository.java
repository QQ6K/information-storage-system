package ru.task.iss.sales.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.task.iss.models.BasketItem;
@Repository
public interface BasketItemsRepository extends JpaRepository<BasketItem,Long> {
    BasketItem findBucketItemByItemId(Long itemId);
}
