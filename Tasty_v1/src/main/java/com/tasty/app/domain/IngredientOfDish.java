package com.tasty.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A IngredientOfDish.
 */
@Entity
@Table(name = "ingredient_of_dish")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IngredientOfDish implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "unit")
    private String unit;

    @Column(name = "quantity")
    private Long quantity;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "favorites", "stepToCooks", "comments", "evaluations", "ingredientOfDishes", "posts", "author", "supperComment" },
        allowSetters = true
    )
    private Post post;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ingredientOfDishes", "type" }, allowSetters = true)
    private Ingredient ingredient;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IngredientOfDish id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnit() {
        return this.unit;
    }

    public IngredientOfDish unit(String unit) {
        this.setUnit(unit);
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getQuantity() {
        return this.quantity;
    }

    public IngredientOfDish quantity(Long quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Post getPost() {
        return this.post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public IngredientOfDish post(Post post) {
        this.setPost(post);
        return this;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public IngredientOfDish ingredient(Ingredient ingredient) {
        this.setIngredient(ingredient);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IngredientOfDish)) {
            return false;
        }
        return id != null && id.equals(((IngredientOfDish) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IngredientOfDish{" +
            "id=" + getId() +
            ", unit='" + getUnit() + "'" +
            ", quantity=" + getQuantity() +
            "}";
    }
}
