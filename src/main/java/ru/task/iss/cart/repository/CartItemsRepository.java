package ru.task.iss.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.task.iss.models.CartItem;

import java.util.Optional;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItem,Long> {
    CartItem findByVendorCode(Long vendorCode);

    void deleteCartItemByVendorCode(Long vendorCode);
}
