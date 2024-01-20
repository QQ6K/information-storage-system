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
    Double price;
    Long amount;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    Collection<SaleItem> saleItems;
}
