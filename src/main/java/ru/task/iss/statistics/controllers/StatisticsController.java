package ru.task.iss.statistics.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.task.iss.models.CartItem;

import java.util.Collection;

@RestController
@RequestMapping(path = "/stat")
@RequiredArgsConstructor
@Slf4j
public class StatisticsController {
    @GetMapping
    public void getItemsFromCart(
    ) {
        log.info("Запрос GET на получение товаров из корзины /cart");
        //return cartService.getItemsFromCart();
    }
}
