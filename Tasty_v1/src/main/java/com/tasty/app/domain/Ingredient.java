package com.tasty.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Ingredient.
 */
@Entity
@Table(name = "ingredient")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ingredient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "other_name")
    private String otherName;

    @OneToMany(mappedBy = "ingredient")
    @JsonIgnoreProperties(value = { "post", "ingredient" }, allowSetters = true)
    private Set<IngredientOfDish> ingredientOfDishes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "ingredients" }, allowSetters = true)
    private IngredientType type;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ingredient id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Ingredient name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOtherName() {
        return this.otherName;
    }

    public Ingredient otherName(String otherName) {
        this.setOtherName(otherName);
        return this;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public Set<IngredientOfDish> getIngredientOfDishes() {
        return this.ingredientOfDishes;
    }

    public void setIngredientOfDishes(Set<IngredientOfDish> ingredientOfDishes) {
        if (this.ingredientOfDishes != null) {
            this.ingredientOfDishes.forEach(i -> i.setIngredient(null));
        }
        if (ingredientOfDishes != null) {
            ingredientOfDishes.forEach(i -> i.setIngredient(this));
        }
        this.ingredientOfDishes = ingredientOfDishes;
    }

    public Ingredient ingredientOfDishes(Set<IngredientOfDish> ingredientOfDishes) {
        this.setIngredientOfDishes(ingredientOfDishes);
        return this;
    }

    public Ingredient addIngredientOfDish(IngredientOfDish ingredientOfDish) {
        this.ingredientOfDishes.add(ingredientOfDish);
        ingredientOfDish.setIngredient(this);
        return this;
    }

    public Ingredient removeIngredientOfDish(IngredientOfDish ingredientOfDish) {
        this.ingredientOfDishes.remove(ingredientOfDish);
        ingredientOfDish.setIngredient(null);
        return this;
    }

    public IngredientType getType() {
        return this.type;
    }

    public void setType(IngredientType ingredientType) {
        this.type = ingredientType;
    }

    public Ingredient type(IngredientType ingredientType) {
        this.setType(ingredientType);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ingredient)) {
            return false;
        }
        return id != null && id.equals(((Ingredient) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ingredient{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", otherName='" + getOtherName() + "'" +
            "}";
    }
}
