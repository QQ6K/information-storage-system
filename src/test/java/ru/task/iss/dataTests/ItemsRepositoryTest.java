package ru.task.iss.dataTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.task.iss.items.repositories.ItemsRepository;
import ru.task.iss.models.Item;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Import()
@ExtendWith(SpringExtension.class)
@Transactional
public class ItemsRepositoryTest {

    @Autowired
    ItemsRepository itemsRepository;

    @Autowired
    private TestEntityManager tem;

    @Test
    @Transactional
    public void addItems() {
        Item item1 = new Item(0L, "Дрель кондитерская", 55.20);
        Item item2 = new Item(0L, "Морская кладь", 160.15);
        Item item3 = new Item(0L, "Быстронаживное дело", 10.00);
        Item item4 = new Item(0L, "Итерационный луч", 99.99);
        Item item5 = new Item(0L, "Роман Широков", 1234.56);
        Item item6 = new Item(0L, "Киндер сюрприз", 200.00);
        Item item7 = new Item(0L, "Буква Ю без перекладины (I0)", 300.00);
        Item item8 = new Item(0L, "Гвоздь Маяковского", 1.00);
        Item item9 = new Item(0L, "Астон Мартин DB6", 10000.00);
        Item item10 = new Item(0L, "Аспирин для котов", 0.25);

        itemsRepository.save(item1);
        itemsRepository.save(item2);
        itemsRepository.save(item3);

       /* tem.persist(item1);
        tem.persist(item2);
        tem.persist(item3);
        tem.persist(item4);
        tem.persist(item5);
        tem.persist(item6);
        tem.persist(item7);
        tem.persist(item8);
        tem.persist(item9);
        tem.persist(item10);*/

        Optional<Item> item = itemsRepository.findById(3L);
        assertEquals(item.get().getName(), item3.getName());

    }

}
