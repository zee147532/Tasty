package com.tiennd.myapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "ingredient")
public class Ingredient {
    @Id
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type")
    private IngredientType type;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "ingredient")
    private List<IngredientOfDish> ingredientOfDish;
}
