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

import javax.annotation.PostConstruct;
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

    // private static final String cron = "0 * * * * *";
    //private static final String cron = "0 0 * * * *";

    // @Scheduled(cron = cron)
    //@Scheduled(fixedDelay = 600000)
    @Scheduled(fixedDelayString = "${wise.scheduling.delay}")
    @PostConstruct
    @Transactional
    public void scheduleDiscount() {
        Item item = getRandomItem();
        if (item == null) {
            throw new CrudException("Отсутствуют товары на складе");
        }
        Discount discount = new Discount();
        discount.setCoefficient(
                (ThreadLocalRandom.current().nextInt(minRandomDiscount, maxRandomDiscount + 1))
        );
        discount.setItemVendorCode(item.getVendorCode());
        discount.setName(item.getName());
        LocalDateTime start = LocalDateTime.now();
        discount.setStarting(start);
        discount.setEnding(start.plusMinutes(1));
        discount.setDiscountCode(0L);
        discountRepository.save(discount);
        discount = discountRepository.findFirstByOrderByIdDesc();
        discount.setDiscountCode(discount.getId() + 10000000);
        discountRepository.save(discount);
        log.info("Скидка id = {}, c кодом {}, для {} {}, % скидки будет {}," +
                        " будет длиться с {} до {}",
                discount.getId(), discount.getDiscountCode(), discount.getItemVendorCode(), discount.getName(), discount.getCoefficient(), discount.getStarting(),
                discount.getEnding());
    }

    private Item getRandomItem() {
        // Long n = itemsRepository.count();
        // Long max = itemsRepository.findMax();
        // Long id = null;
        // if (n != 0) {
        //    log.info("Репозиторий товаров id = {}", n);
        //    log.info("Репозиторий товаров max id = {}", max);
        Item item = itemsRepository.getRandomItem();
        //   log.info("Случайный товар id = {}", id);
        // }
        //throw new CrudException("Empty repository");
        //itemsRepository.findByVendorCode(vendorCode);
        return item;
    }

    public Discount getCurrentDiscount() {
        return discountRepository.findFirstByOrderByIdDesc();
    }

    public Discount getPastDiscount() {
        return discountRepository.findPastDiscount();
    }


}
