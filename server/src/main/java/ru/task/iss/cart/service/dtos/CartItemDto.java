package ru.task.iss.cart.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long vendorCode;
    String name;
    Double price;
    Long amount;
}
