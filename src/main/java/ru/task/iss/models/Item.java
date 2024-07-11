package ru.task.iss.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "vendor_code")
    Long vendorCode;
    String name;
    Long price;
    Long amount;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    Collection<SaleItem> saleItems;

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
