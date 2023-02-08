package ru.task.iss.models;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter //TODO: change to Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "discounts")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    int val;
    @ManyToOne
    Item item;

}
