package com.tiennd.myapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Table(name = "customer")
@Entity
@Data
public class Customer {
    @Id
    private String username;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String email;

    private Boolean status;

    private String password;

    private Boolean gender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profession")
    private Profession profession;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private List<Favorites> favorites;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    private List<Post> posts;
}
