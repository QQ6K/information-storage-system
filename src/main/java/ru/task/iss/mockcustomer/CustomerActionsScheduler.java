package ru.task.iss.mockcustomer;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.cart.service.CartService;
import ru.task.iss.items.repositories.ItemsRepository;
import ru.task.iss.models.CartItem;
import ru.task.iss.models.Item;
import ru.task.iss.security.dtos.JwtRequest;
import ru.task.iss.security.service.AuthService;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@EnableScheduling
@Transactional(readOnly = true)
public class CustomerActionsScheduler {

    private final AuthService authService;

    private final CartService cartService;

    private final ItemsRepository itemsRepository;

    @Scheduled(fixedDelay = 10000)
    @Transactional
    public void scheduleDiscount() throws JsonProcessingException {
        int action = ThreadLocalRandom.current().nextInt(1, 5);
        switch (action) {
            case (1): addItem(); break;
            case (2): addItem(); break;
            case (3): addItem(); break;
            case (4): {addItem(); buy(); break;}
        }
    }

    private void addItem() {
        Item item = itemsRepository.getRandomItem();
        CartItem cartItem = new CartItem();
        cartItem.setAmount(ThreadLocalRandom.current().nextInt(1, 3));
        cartItem.setName(item.getName());
        cartItem.setPrice(item.getPrice());
        cartItem.setVendorCode(item.getVendorCode());
        cartItem.setItemId(item.getId());
        cartService.addItemToCart(cartItem);
    }

    private void buy() {
        cartService.buyCart();
    }

}
