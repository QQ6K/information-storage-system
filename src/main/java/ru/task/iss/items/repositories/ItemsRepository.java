package ru.task.iss.items.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.task.iss.models.Item;

@Repository
public interface ItemsRepository extends JpaRepository<Item,Long> {

}
