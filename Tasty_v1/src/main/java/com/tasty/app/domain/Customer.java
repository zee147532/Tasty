package com.tasty.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tasty.app.domain.enumeration.Gender;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private Boolean status;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "confirmed")
    private Boolean confirmed;

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties(value = { "customer", "post" }, allowSetters = true)
    private Set<Favorites> favorites = new HashSet<>();

    @OneToMany(mappedBy = "author")
    @JsonIgnoreProperties(
        value = { "favorites", "stepToCooks", "comments", "evaluations", "ingredientOfDishes", "typeOfDishes", "author" },
        allowSetters = true
    )
    private Set<Post> posts = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "customers" }, allowSetters = true)
    private Profession professtion;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Customer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public Customer username(String username) {
        this.setUsername(username);
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public Customer password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return this.fullName;
    }

    public Customer fullName(String fullName) {
        this.setFullName(fullName);
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Customer phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }

    public Customer email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public Customer status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Gender getGender() {
        return this.gender;
    }

    public Customer gender(Gender gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Boolean getConfirmed() {
        return this.confirmed;
    }

    public Customer confirmed(Boolean confirmed) {
        this.setConfirmed(confirmed);
        return this;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Set<Favorites> getFavorites() {
        return this.favorites;
    }

    public void setFavorites(Set<Favorites> favorites) {
        if (this.favorites != null) {
            this.favorites.forEach(i -> i.setCustomer(null));
        }
        if (favorites != null) {
            favorites.forEach(i -> i.setCustomer(this));
        }
        this.favorites = favorites;
    }

    public Customer favorites(Set<Favorites> favorites) {
        this.setFavorites(favorites);
        return this;
    }

    public Customer addFavorites(Favorites favorites) {
        this.favorites.add(favorites);
        favorites.setCustomer(this);
        return this;
    }

    public Customer removeFavorites(Favorites favorites) {
        this.favorites.remove(favorites);
        favorites.setCustomer(null);
        return this;
    }

    public Set<Post> getPosts() {
        return this.posts;
    }

    public void setPosts(Set<Post> posts) {
        if (this.posts != null) {
            this.posts.forEach(i -> i.setAuthor(null));
        }
        if (posts != null) {
            posts.forEach(i -> i.setAuthor(this));
        }
        this.posts = posts;
    }

    public Customer posts(Set<Post> posts) {
        this.setPosts(posts);
        return this;
    }

    public Customer addPost(Post post) {
        this.posts.add(post);
        post.setAuthor(this);
        return this;
    }

    public Customer removePost(Post post) {
        this.posts.remove(post);
        post.setAuthor(null);
        return this;
    }

    public Profession getProfesstion() {
        return this.professtion;
    }

    public void setProfesstion(Profession profession) {
        this.professtion = profession;
    }

    public Customer professtion(Profession profession) {
        this.setProfesstion(profession);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", email='" + getEmail() + "'" +
            ", status='" + getStatus() + "'" +
            ", gender='" + getGender() + "'" +
            ", confirmed='" + getConfirmed() + "'" +
            "}";
    }
}
