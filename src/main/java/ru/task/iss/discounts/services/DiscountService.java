package ru.task.iss.discounts.services;


import org.springframework.data.domain.Pageable;
import ru.task.iss.common.PageDTO;
import ru.task.iss.models.Discount;
import ru.task.iss.models.Item;

import java.util.List;

public interface DiscountService {
   // List<Discount> readHistory();
    PageDTO<Discount> getDiscountsPage(Pageable pageable);
}
