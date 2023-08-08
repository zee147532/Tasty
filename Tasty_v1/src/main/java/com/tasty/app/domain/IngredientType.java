package com.tasty.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A IngredientType.
 */
@Entity
@Table(name = "ingredient_type")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IngredientType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "type")
    @JsonIgnoreProperties(value = { "ingredientOfDishes", "type" }, allowSetters = true)
    private Set<Ingredient> ingredients = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IngredientType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public IngredientType name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        if (this.ingredients != null) {
            this.ingredients.forEach(i -> i.setType(null));
        }
        if (ingredients != null) {
            ingredients.forEach(i -> i.setType(this));
        }
        this.ingredients = ingredients;
    }

    public IngredientType ingredients(Set<Ingredient> ingredients) {
        this.setIngredients(ingredients);
        return this;
    }

    public IngredientType addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        ingredient.setType(this);
        return this;
    }

    public IngredientType removeIngredient(Ingredient ingredient) {
        this.ingredients.remove(ingredient);
        ingredient.setType(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IngredientType)) {
            return false;
        }
        return id != null && id.equals(((IngredientType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IngredientType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
