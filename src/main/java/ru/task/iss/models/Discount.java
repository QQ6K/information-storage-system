package ru.task.iss.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter //TODO: change to Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "discounts")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double valCoefficient;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Item item;
    private LocalDateTime starting;
    private LocalDateTime ending;

}
