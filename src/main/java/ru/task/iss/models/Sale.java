package ru.task.iss.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code")
    private Long salesCode;
    private double price;
    @Column(name = "final_price")
    private double finalPrice;
    @Column(name = "discount_sum")
    private double discountSum;
    @Column(name = "discount_code")
    private Long discountCode;
    private LocalDateTime createdOn;
}