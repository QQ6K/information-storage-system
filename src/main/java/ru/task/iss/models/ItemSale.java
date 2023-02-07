package ru.task.iss.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    Discount discount;
}
