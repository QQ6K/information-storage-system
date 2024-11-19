package ru.task.miss.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.task.miss.models.CartProduct;

import java.util.Optional;

@Repository
public interface CartProductsRepository extends JpaRepository<CartProduct, Long> {

    Optional<CartProduct> findByVendorCode(Long vendorCode);

    CartProduct getCartProductByVendorCode(Long vendorCode);

    void deleteCartProductByVendorCode(Long vendorCode);

}
