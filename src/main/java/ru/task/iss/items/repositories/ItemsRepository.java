package ru.task.iss.items.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.task.iss.models.Item;

import java.util.Optional;

@Repository
public interface ItemsRepository extends JpaRepository<Item, Long> {
    @Query(value = "SELECT ID FROM ITEMS ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    public Long getRandom();

    @Query(value = "SELECT MAX(ID) FROM ITEMS", nativeQuery = true)
    public Long findMax();

    Page<Item> findAll(Pageable pageable);

    Optional<Item> findByVendorCode(Long vendorCore);

    @Query(value = "select vendor_code from ITEMS order by RANDOM() LIMIT 1", nativeQuery = true)
    public Long getRandomVendorCode();

    @Query(value = "select * from ITEMS order by RANDOM() LIMIT 1", nativeQuery = true)
    public Item getRandomItem();
}
