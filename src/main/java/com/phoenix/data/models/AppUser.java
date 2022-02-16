package com.phoenix.data.models;

import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(length = 500)
    private String address;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    @OneToOne(cascade = CascadeType.PERSIST)
    @Getter
    private final Cart myCart;

    public AppUser(){
        myCart = new Cart();
    }

}
