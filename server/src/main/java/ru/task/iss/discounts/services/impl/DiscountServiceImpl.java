package ru.task.iss.discounts.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.common.PageDTO;
import ru.task.iss.common.PageToPageDTOMapper;
import ru.task.iss.discounts.repository.DiscountRepository;
import ru.task.iss.discounts.services.DiscountService;
import ru.task.iss.models.Discount;
import ru.task.iss.models.Item;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;

    private final PageToPageDTOMapper<Discount> pageToPageDTOMapper;

    @Override
    public PageDTO<Discount> getDiscountsPage(Pageable pageable) {
        log.info("Получить страницу скидок");
        Page<Discount> discountPage = discountRepository.findAll(pageable);
        return pageToPageDTOMapper.pageToPageDTO(discountPage);
    }

}
