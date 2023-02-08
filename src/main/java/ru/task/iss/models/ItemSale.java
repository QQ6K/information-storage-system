package ru.task.iss.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemSale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Item item;
    int count;
   // @OneToOne
    Discount discount;
}
