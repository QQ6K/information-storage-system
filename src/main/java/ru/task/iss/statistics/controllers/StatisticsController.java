package ru.task.iss.statistics.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.task.iss.exceptions.BadRequestException;
import ru.task.iss.models.SalesItemStatDto;
import ru.task.iss.models.StatisticData;
import ru.task.iss.statistics.services.StatisticsService;
import ru.task.iss.statistics.services.impl.StatisticsServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;


@RestController
@RequestMapping(path = "/stat")
@RequiredArgsConstructor
@Slf4j
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Secured("ROLE_ADMIN")
    @GetMapping("/{vendorCode}")
    public SalesItemStatDto getStatItem(@PathVariable Long vendorCode
    ) {
        log.info("Запрос GET статистика /{}", vendorCode);
        return statisticsService.getStatForItem(vendorCode);
    }

    @GetMapping
    @Secured("ROLE_ADMIN")
    public Collection<StatisticData> getStat(
    ) {
        log.info("Запрос GET статистика /stat");
        return statisticsService.getStat();
    }

    @GetMapping("/calculate")
    @Secured("ROLE_ADMIN")
    public void getRecalculate(
    ) {
        log.info("Запрос GET пересчет /calculate");
        statisticsService.getRecalculate();
    }

    /*@GetMapping("/{vendorCode}")
    public Collection<StatisticData> getStatForItem(
    ) {
        log.info("Запрос GET на получение товаров из корзины /cart");
        return statisticsService.getStatForItem();
    }*/

    @GetMapping("/between")
    @Secured("ROLE_ADMIN")
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
