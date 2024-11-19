package ru.task.miss.servicies;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.task.miss.repositories.DiscountRepository;
import ru.task.miss.interfaces.DiscountService;
import ru.task.miss.dtos.DiscountDto;
import ru.task.miss.mappers.DiscountMapper;
import ru.task.miss.models.Discount;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;

    //private final PageToPageDTOMapper<Discount> pageToPageDTOMapper;

    @Override
    public Page<DiscountDto> getDiscountsPage(Pageable pageable) {
        log.info("Получить страницу скидок");
        Page<Discount> discountsPage = discountRepository.findAll(pageable);
        List<DiscountDto> discountDtos = discountsPage.getContent().stream()
                .map(DiscountMapper::toDiscountDto)
                .collect(Collectors.toList());

        return new PageImpl<>(discountDtos, pageable, discountsPage.getTotalElements());
    }

}
