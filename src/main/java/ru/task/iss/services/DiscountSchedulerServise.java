package ru.task.iss.services;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.exceptions.CrudException;
import ru.task.iss.models.Discount;
import ru.task.iss.models.Item;
import ru.task.iss.repositories.DiscountRepository;
import ru.task.iss.repositories.ItemRepository;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@EnableScheduling
@Transactional(readOnly = true)
public class DiscountSchedulerServise {

    int minRandomDiscount = 5;
    int maxRandomDiscount = 10;

    private final DiscountRepository discountRepository;
    private final ItemRepository itemRepository;

    private static final String cron = "0 0 * * * *";

    @Scheduled(cron = cron)
    private void scheduleDiscount() {
        Discount discount = new Discount();
        discount.setVal(ThreadLocalRandom.current().nextInt(minRandomDiscount,maxRandomDiscount+1));
        discount.setItem(getRandomItem());
        discountRepository.save(discount);
    }

    private Item getRandomItem(){
        return itemRepository.findById(
                ThreadLocalRandom.current()
                        .nextLong(itemRepository.count()))
                .orElseThrow(() -> new CrudException("Cannot find random Item element"));
    }

    private Discount getDiscount(){
        return discountRepository.findFirstByOrderByIdDesc();
    }

}
