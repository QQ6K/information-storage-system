package ru.task.iss.discounts.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.task.iss.models.Discount;

import java.util.List;

@RestController
@RequestMapping("/discounts")
@RequiredArgsConstructor
public class DiscountControllers {

    public List<Discount> getDiscountHistory(){
        return null;
    }

}
