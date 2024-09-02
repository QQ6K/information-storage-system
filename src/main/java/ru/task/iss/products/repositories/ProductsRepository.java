package ru.task.iss.products.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.task.iss.models.Product;

import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT ID FROM products ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    public Long getRandom();

    @Query(value = "SELECT MAX(ID) FROM products", nativeQuery = true)
    public Long findMax();

    Page<Product> findAll(Pageable pageable);

    Optional<Product> findByVendorCode(Long vendorCore);

    @Query(value = "select vendor_code from products order by RANDOM() LIMIT 1", nativeQuery = true)
    public Long getRandomVendorCode();

    @Query(value = "select * from products order by RANDOM() LIMIT 1", nativeQuery = true)
    public Product getRandomProduct();
}
