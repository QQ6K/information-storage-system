package ru.task.iss.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sale_items")
public class SaleItem{
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
    private double price;
    private Long amount;
    private double discount;
    private Long discountCode;
    private double finalPrice;
    private double totalPrice;
    private LocalDateTime createdOn;
}