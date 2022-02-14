package com.phoenix.data.models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
//@Builder
@NoArgsConstructor
public class Cart {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    @CreationTimestamp
    private LocalDateTime createAt;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Item> items;

    @Transient
    private double totalPrice;

    public void addItem(Item item){
        if(items == null) items = new ArrayList<>();

        items.add(item);
    }


}
