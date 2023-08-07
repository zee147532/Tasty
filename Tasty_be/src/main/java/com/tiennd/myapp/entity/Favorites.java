package com.tiennd.myapp.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "favorites")
public class Favorites {
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer")
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post")
    private Post post;
}
