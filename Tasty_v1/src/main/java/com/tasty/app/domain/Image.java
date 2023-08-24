package com.tasty.app.domain;

import com.tasty.app.domain.enumeration.TypeOfImage;

import javax.persistence.*;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private TypeOfImage type;

    private String uri;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "posts")
    private Post post;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ingredient")
    private Ingredient ingredient;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer")
    private Customer customer;

    @Override
    public String toString() {
        return "Image{" +
            "id=" + id +
            ", type=" + type +
            ", uri='" + uri + '\'' +
            ", post=" + post +
            ", ingredient=" + ingredient +
            ", customer=" + customer +
            '}';
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeOfImage getType() {
        return type;
    }

    public void setType(TypeOfImage type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Image() {
    }

    public Image(Long id, TypeOfImage type, String uri, Post post, Ingredient ingredient, Customer customer) {
        this.id = id;
        this.type = type;
        this.uri = uri;
        this.post = post;
        this.ingredient = ingredient;
        this.customer = customer;
    }
}
