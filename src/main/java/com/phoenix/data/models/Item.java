package com.phoenix.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    private Product product;

    private Integer quantityAdded;

    public Item(Product product, Integer quantityAdded){
        if(quantityAdded <= product.getQuantity()){
            this.quantityAdded = quantityAdded;
        }
        this.product = product;
    }

    public void addQuantity(Integer quantityAdded){
        if(quantityAdded <= product.getQuantity()){
            this.quantityAdded = quantityAdded;
        }else {
            this.quantityAdded = 0;
        }
    }
}
