package com.tiennd.myapp.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ingredient_of_dish")
public class IngredientOfDish {
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post")
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ingredient")
    private Ingredient ingredient;

    private String unit;

    private Long quantity;
}
