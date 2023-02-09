package ru.task.iss.discounts.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.discounts.repository.DiscountRepository;
import ru.task.iss.discounts.services.DiscountService;
import ru.task.iss.models.Discount;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class DiscountServiceImpl implements DiscountService {

    DiscountRepository discountRepository;

    @Override
    public List<Discount> readHistory(){
        return discountRepository.findAll();
    }

}
