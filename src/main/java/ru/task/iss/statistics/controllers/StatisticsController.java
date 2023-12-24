package ru.task.iss.statistics.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.task.iss.exceptions.BadRequestException;
import ru.task.iss.statistics.services.StatisticsService;
import ru.task.iss.statistics.services.impl.StatisticsServiceImpl;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping(path = "/stat")
@RequiredArgsConstructor
@Slf4j
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/calculate")
    public void getRecalculate(
    ) {
        log.info("Запрос GET пересчет /calculate");
        statisticsService.getRecalculate();
    }

    @GetMapping("/{vendorCode}")
    public void getStatForItem(
    ) {
        log.info("Запрос GET на получение товаров из корзины /cart");
        //return cartService.getItemsFromCart();
    }

    @GetMapping
    public void getStatForDuration(
    ) {
        log.info("Запрос GET на получение товаров из корзины /cart");
        //return cartService.getItemsFromCart();
    }

  /*  @GetMapping
    public void getPageStat(
        @RequestParam(defaultValue = "0", required = false) Integer from,
        @RequestParam(defaultValue = "10", required = false) Integer size,
        @RequestParam(required = false) Integer page, HttpServletRequest request) {
            Pageable pageable;
            if (size == null) {
                pageable = Pageable.unpaged();
            } else if (size <= 0) {
                throw new BadRequestException("Ошибка параметров пагинации");
            } else {
                if (page == null) {page =  from / size;};
                pageable = PageRequest.of(page, size);
            }
            log.info("Запрос GET /items?size={}&page={}", size, page);
        //return cartService.getItemsFromCart();
    }*/

}
