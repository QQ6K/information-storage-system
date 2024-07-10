package ru.task.iss.discounts.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.task.iss.common.PageDTO;
import ru.task.iss.discounts.services.dto.DiscountDto;
import ru.task.iss.models.Discount;
import ru.task.iss.models.Item;

import java.util.List;

public interface DiscountService {
   // List<Discount> readHistory();
   Page<DiscountDto> getDiscountsPage(Pageable pageable);
}
