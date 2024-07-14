package ru.task.iss.discounts.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.task.iss.discounts.services.dto.DiscountDto;

public interface DiscountService {
    // List<Discount> readHistory();
    Page<DiscountDto> getDiscountsPage(Pageable pageable);
}
