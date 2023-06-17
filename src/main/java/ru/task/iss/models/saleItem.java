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
@Entity@Table(name = "salesItems")
public class saleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Item item;
    private double fixedItemPrice;
    private int count;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Sale sale;
    private Long discount;
}
