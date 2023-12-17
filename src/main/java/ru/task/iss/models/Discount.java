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
    private double coefficient;
    @Column(name = "item_vendor_code")
    private Long itemVendorCode;
    private LocalDateTime starting;
    private LocalDateTime ending;

}