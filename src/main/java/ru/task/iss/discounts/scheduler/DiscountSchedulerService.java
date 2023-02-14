package ru.task.iss.discounts.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.discounts.repository.DiscountRepository;
import ru.task.iss.exceptions.CrudException;
import ru.task.iss.items.repositories.ItemsRepository;
import ru.task.iss.models.Discount;
import ru.task.iss.models.Item;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@EnableScheduling
@Transactional(readOnly = true)
@Slf4j
public class DiscountSchedulerService {

    int minRandomDiscount = 5;
    int maxRandomDiscount = 10;

    private final DiscountRepository discountRepository;
    private final ItemsRepository itemsRepository;

    private static final String cron = "0 * * * * *";
    //private static final String cron = "0 0 * * * *";

    @Scheduled(cron = cron)
    public void scheduleDiscount() {
        Discount discount = new Discount();
        discount.setValCoefficient(
                100.00 - ThreadLocalRandom.current().nextInt(minRandomDiscount, maxRandomDiscount + 1) / 100.00);
        discount.setItem(getRandomItem());
        discount.setStarting(LocalDateTime.now());
        discount.setEnding(discount.getStarting().plusMinutes(1));
        discount = discountRepository.save(discount);
        log.info("Шайтан машина делает скидку id = {}, для {}, размером {}%, будет длиться с {} до {}",
                discount.getId(), discount.getItem().getName(), discount.getValCoefficient(), discount.getStarting(),
                discount.getEnding());
    }

    private Item getRandomItem() {
        Long n = itemsRepository.count();
        log.info("Репозиторий товаров id = {}", n);
        Long i = ThreadLocalRandom.current()
                .nextLong(itemsRepository.count());
        log.info("Слушайный товар id = {}", i);
        return itemsRepository.findById(i
                )
                .orElseThrow(() -> new CrudException("Cannot find random Item element"));
    }

    public Discount getCurrentDiscount() {
        return discountRepository.findFirstByOrderByIdDesc();
    }

    public Discount getPastDiscount() {
        return discountRepository.findPastDiscount();
    }


}
