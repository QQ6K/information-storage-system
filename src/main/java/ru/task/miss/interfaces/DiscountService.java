package ru.task.miss.interfaces;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.task.miss.dtos.DiscountDto;

public interface DiscountService {
    // List<Discount> readHistory();
    Page<DiscountDto> getDiscountsPage(Pageable pageable);
}
