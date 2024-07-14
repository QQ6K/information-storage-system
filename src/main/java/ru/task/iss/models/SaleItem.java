package ru.task.iss.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sale_items")
public class SaleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "sale_code")
    private Long saleCode;
    @Column(name = "vendor_code")
    private Long vendorCode;
    @Column(name = "item_id")
    private Long itemId;
    private String name;
    private Long price;
    private Long amount;
    private Long discount;
    private Long discountCode;
    private Long finalPrice;
    private Long totalPrice;
    private LocalDateTime createdOn;
}