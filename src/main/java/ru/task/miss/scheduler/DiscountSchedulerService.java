package ru.task.miss.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.task.miss.repositories.DiscountRepository;
import ru.task.miss.exceptions.CrudException;
import ru.task.miss.repositories.ProductsRepository;
import ru.task.miss.models.Discount;
import ru.task.miss.models.Product;

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
    private final ProductsRepository productsRepository;

    // private static final String cron = "0 * * * * *";
    //private static final String cron = "0 0 * * * *";

    // @Scheduled(cron = cron)
    //@Scheduled(fixedDelay = 600000)
    @Scheduled(fixedDelayString = "${wise.scheduling.delay}")
    @PostConstruct
    @Transactional
    public void scheduleDiscount() {
        Product product = getRandomProduct();
        if (product == null) {
            throw new CrudException("Отсутствуют товары на складе");
        }
        Discount discount = new Discount();
        discount.setCoefficient(
                (ThreadLocalRandom.current().nextInt(minRandomDiscount, maxRandomDiscount + 1))
        );
        discount.setProductVendorCode(product.getVendorCode());
        discount.setName(product.getName());
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
                discount.getId(), discount.getDiscountCode(), discount.getProductVendorCode(), discount.getName(), discount.getCoefficient(), discount.getStarting(),
                discount.getEnding());
    }

    private Product getRandomProduct() {
        // Long n = itemsRepository.count();
        // Long max = itemsRepository.findMax();
        // Long id = null;
        // if (n != 0) {
        //    log.info("Репозиторий товаров id = {}", n);
        //    log.info("Репозиторий товаров max id = {}", max);
        Product product = productsRepository.getRandomProduct();
        //   log.info("Случайный товар id = {}", id);
        // }
        //throw new CrudException("Empty repository");
        //itemsRepository.findByVendorCode(vendorCode);
        return product;
    }

    public Discount getCurrentDiscount() {
        return discountRepository.findFirstByOrderByIdDesc();
    }

    public Discount getPastDiscount() {
        return discountRepository.findPastDiscount();
    }


}
