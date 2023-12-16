package ru.task.iss.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.task.iss.models.Sale;

@Repository
public interface SalesRepository extends JpaRepository<Sale,Long> {

    @Query(value = "SELECT MAX(code) FROM SALES", nativeQuery = true)
    Long findMaximum();

}
