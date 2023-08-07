package com.tiennd.myapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Profession {
    @Id
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "profession")
    private List<Customer> customers;
}
