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
//import ru.task.iss.sales.services.SalesService;
import ru.task.iss.sales.services.dtos.UpdateBucketShortDto;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@EnableScheduling
@Transactional(readOnly = true)
@Slf4j
public class DiscountSchedulerService {

    private static final int minRandomDiscount = 5;
    private static final int maxRandomDiscount = 10;

    private final DiscountRepository discountRepository;
    private final ItemsRepository itemsRepository;

    private static final String cron = "0 * * * * *";
    //private static final String cron = "0 0 * * * *";

   // @Scheduled(cron = cron)
   @Scheduled(fixedDelay = 10000)
   @Transactional
    public void scheduleDiscount() {
        Discount discount = new Discount();
        discount.setValCoefficient(
                (100.00 - ThreadLocalRandom.current().nextInt(minRandomDiscount, maxRandomDiscount + 1))
                        / 100.00);
        discount.setItem(getRandomItem());
        discount.setStarting(LocalDateTime.now());
        discount.setEnding(discount.getStarting().plusMinutes(1));
        discount = discountRepository.save(discount);
        log.info("Шайтан машина делает скидку id = {}, для {}, коээфициент для цены будет {}," +
                        " будет длиться с {} до {}",
                discount.getId(), discount.getItem().getName(), discount.getValCoefficient(), discount.getStarting(),
                discount.getEnding());
    }

    private Item getRandomItem() {
        Long n = itemsRepository.count();
        Long max = itemsRepository.findMax();
        Long id = null;
        if (n != 0) {
            log.info("Репозиторий товаров id = {}", n);
            log.info("Репозиторий товаров max id = {}", max);
            id = itemsRepository.getRandom();
            log.info("Случайный товар id = {}", id);
        }
        else throw new CrudException("Empty repository");
        return itemsRepository.findById(id
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
