package ru.task.iss.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sales")
@Builder
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "sale_code")
    private Long salesCode;
    private Long price;
    @Column(name = "final_price")
    private Long finalPrice;
    @Column(name = "discount_sum")
    private Long discountSum;
    @Column(name = "discount_code")
    private Long discountCode;
    private LocalDateTime createdOn;
}