package ru.task.iss.discounts.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.task.iss.discounts.services.DiscountService;
import ru.task.iss.models.Discount;

import java.util.List;

@RestController
@RequestMapping("/discounts")
@RequiredArgsConstructor
public class DiscountControllers {

    DiscountService discountService;

    @GetMapping
    public List<Discount> getDiscountHistory(){
        return discountService.readHistory();
    }

}
