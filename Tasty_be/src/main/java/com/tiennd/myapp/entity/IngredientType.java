package com.tiennd.myapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "ingredient_type")
public class IngredientType {
    @Id
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "type")
    private List<Ingredient> ingredients;
}
