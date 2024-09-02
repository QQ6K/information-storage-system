package ru.task.iss.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column(name = "discount_code")
    private Long discountCode;
    private Integer coefficient;
    @Column(name = "product_vendor_code")
    private Long productVendorCode;
    private String name;
    private LocalDateTime starting;
    private LocalDateTime ending;

}