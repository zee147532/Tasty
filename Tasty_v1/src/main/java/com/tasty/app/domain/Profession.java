package com.tasty.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Profession.
 */
@Entity
@Table(name = "profession")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Profession implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "professtion")
    @JsonIgnoreProperties(value = { "favorites", "posts", "professtion" }, allowSetters = true)
    private Set<Customer> customers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Profession id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Profession name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Customer> getCustomers() {
        return this.customers;
    }

    public void setCustomers(Set<Customer> customers) {
        if (this.customers != null) {
            this.customers.forEach(i -> i.setProfesstion(null));
        }
        if (customers != null) {
            customers.forEach(i -> i.setProfesstion(this));
        }
        this.customers = customers;
    }

    public Profession customers(Set<Customer> customers) {
        this.setCustomers(customers);
        return this;
    }

    public Profession addCustomer(Customer customer) {
        this.customers.add(customer);
        customer.setProfesstion(this);
        return this;
    }

    public Profession removeCustomer(Customer customer) {
        this.customers.remove(customer);
        customer.setProfesstion(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profession)) {
            return false;
        }
        return id != null && id.equals(((Profession) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Profession{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
