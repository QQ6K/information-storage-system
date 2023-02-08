package ru.task.iss.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.task.iss.models.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {

}
