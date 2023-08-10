package com.tasty.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A DishType.
 */
@Entity
@Table(name = "dish_type")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DishType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "type")
    @JsonIgnoreProperties(value = { "type", "post" }, allowSetters = true)
    private Set<TypeOfDish> typeOfDishes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DishType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public DishType name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<TypeOfDish> getTypeOfDishes() {
        return this.typeOfDishes;
    }

    public void setTypeOfDishes(Set<TypeOfDish> typeOfDishes) {
        if (this.typeOfDishes != null) {
            this.typeOfDishes.forEach(i -> i.setType(null));
        }
        if (typeOfDishes != null) {
            typeOfDishes.forEach(i -> i.setType(this));
        }
        this.typeOfDishes = typeOfDishes;
    }

    public DishType typeOfDishes(Set<TypeOfDish> typeOfDishes) {
        this.setTypeOfDishes(typeOfDishes);
        return this;
    }

    public DishType addTypeOfDish(TypeOfDish typeOfDish) {
        this.typeOfDishes.add(typeOfDish);
        typeOfDish.setType(this);
        return this;
    }

    public DishType removeTypeOfDish(TypeOfDish typeOfDish) {
        this.typeOfDishes.remove(typeOfDish);
        typeOfDish.setType(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DishType)) {
            return false;
        }
        return id != null && id.equals(((DishType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DishType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
