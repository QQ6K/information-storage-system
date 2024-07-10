package ru.task.iss.discounts.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.task.iss.common.PageDTO;
import ru.task.iss.discounts.services.DiscountService;
import ru.task.iss.discounts.services.dto.DiscountDto;
import ru.task.iss.exceptions.BadRequestException;
import ru.task.iss.exceptions.CrudException;
import ru.task.iss.models.Discount;

@RestController
@RequestMapping("/discounts")
@RequiredArgsConstructor
@Slf4j
public class DiscountControllers {

    private final DiscountService discountService;

    /*@GetMapping
    public List<Discount> getDiscountHistory(){
        return discountService.readHistory();
    }*/

    @GetMapping
    public Page<DiscountDto> getHistory(
            @RequestParam(defaultValue = "0", required = false) Integer from,
            @RequestParam(defaultValue = "10", required = false) Integer size,
            @RequestParam(required = false) Integer page) {
        Pageable pageable;
        if (size == null) {
            pageable = Pageable.unpaged();
        } else if (size <= 0) {
            throw new CrudException("Ошибка параметров пагинации");
        } else {
            if (page == null) {
                page = from / size;
            }
            pageable = PageRequest.of(page, size);
        }
        log.info("Запрос GET /discounts?size={}&page={}", size, page);
        return discountService.getDiscountsPage(pageable);
    }
}
