package com.tiennd.myapp.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "admin")
public class Admin {
    @Id
    private String username;

    private String password;
}
