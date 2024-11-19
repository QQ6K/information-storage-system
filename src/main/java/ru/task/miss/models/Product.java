package ru.task.miss.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "vendor_code")
    Long vendorCode;
    String name;
    Long price;
    Long amount;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    Collection<SaleProduct> saleProducts;

    public void setPrice(Double priceDouble) {
        if (priceDouble != null) {
            this.amount = Double.doubleToLongBits(priceDouble);
        } else {
            this.amount = null;
        }
    }

    /*public Double getPrice() {
        if (amount != null) {
            return (amount / 100.0);
        } else {
            return null;
        }
    }*/
}
