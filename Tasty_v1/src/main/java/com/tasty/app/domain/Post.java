package com.tasty.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Post.
 */
@Entity
@Table(name = "post")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @OneToMany(mappedBy = "post")
    @JsonIgnoreProperties(value = { "customer", "post" }, allowSetters = true)
    private Set<Favorites> favorites = new HashSet<>();

    @OneToMany(mappedBy = "post")
    @JsonIgnoreProperties(value = { "post" }, allowSetters = true)
    private Set<StepToCook> stepToCooks = new HashSet<>();

    @OneToMany(mappedBy = "post")
    @JsonIgnoreProperties(value = { "post" }, allowSetters = true)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "post")
    @JsonIgnoreProperties(value = { "post" }, allowSetters = true)
    private Set<Evaluation> evaluations = new HashSet<>();

    @OneToMany(mappedBy = "post")
    @JsonIgnoreProperties(value = { "post", "ingredient" }, allowSetters = true)
    private Set<IngredientOfDish> ingredientOfDishes = new HashSet<>();

    @OneToMany(mappedBy = "supperComment")
    @JsonIgnoreProperties(
        value = { "favorites", "stepToCooks", "comments", "evaluations", "ingredientOfDishes", "posts", "author", "supperComment" },
        allowSetters = true
    )
    private Set<Post> posts = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "favorites", "posts", "professtion" }, allowSetters = true)
    private Customer author;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "favorites", "stepToCooks", "comments", "evaluations", "ingredientOfDishes", "posts", "author", "supperComment" },
        allowSetters = true
    )
    private Post supperComment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Post id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Post title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public Post content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return this.description;
    }

    public Post description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public Post status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Post createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Set<Favorites> getFavorites() {
        return this.favorites;
    }

    public void setFavorites(Set<Favorites> favorites) {
        if (this.favorites != null) {
            this.favorites.forEach(i -> i.setPost(null));
        }
        if (favorites != null) {
            favorites.forEach(i -> i.setPost(this));
        }
        this.favorites = favorites;
    }

    public Post favorites(Set<Favorites> favorites) {
        this.setFavorites(favorites);
        return this;
    }

    public Post addFavorites(Favorites favorites) {
        this.favorites.add(favorites);
        favorites.setPost(this);
        return this;
    }

    public Post removeFavorites(Favorites favorites) {
        this.favorites.remove(favorites);
        favorites.setPost(null);
        return this;
    }

    public Set<StepToCook> getStepToCooks() {
        return this.stepToCooks;
    }

    public void setStepToCooks(Set<StepToCook> stepToCooks) {
        if (this.stepToCooks != null) {
            this.stepToCooks.forEach(i -> i.setPost(null));
        }
        if (stepToCooks != null) {
            stepToCooks.forEach(i -> i.setPost(this));
        }
        this.stepToCooks = stepToCooks;
    }

    public Post stepToCooks(Set<StepToCook> stepToCooks) {
        this.setStepToCooks(stepToCooks);
        return this;
    }

    public Post addStepToCook(StepToCook stepToCook) {
        this.stepToCooks.add(stepToCook);
        stepToCook.setPost(this);
        return this;
    }

    public Post removeStepToCook(StepToCook stepToCook) {
        this.stepToCooks.remove(stepToCook);
        stepToCook.setPost(null);
        return this;
    }

    public Set<Comment> getComments() {
        return this.comments;
    }

    public void setComments(Set<Comment> comments) {
        if (this.comments != null) {
            this.comments.forEach(i -> i.setPost(null));
        }
        if (comments != null) {
            comments.forEach(i -> i.setPost(this));
        }
        this.comments = comments;
    }

    public Post comments(Set<Comment> comments) {
        this.setComments(comments);
        return this;
    }

    public Post addComment(Comment comment) {
        this.comments.add(comment);
        comment.setPost(this);
        return this;
    }

    public Post removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setPost(null);
        return this;
    }

    public Set<Evaluation> getEvaluations() {
        return this.evaluations;
    }

    public void setEvaluations(Set<Evaluation> evaluations) {
        if (this.evaluations != null) {
            this.evaluations.forEach(i -> i.setPost(null));
        }
        if (evaluations != null) {
            evaluations.forEach(i -> i.setPost(this));
        }
        this.evaluations = evaluations;
    }

    public Post evaluations(Set<Evaluation> evaluations) {
        this.setEvaluations(evaluations);
        return this;
    }

    public Post addEvaluation(Evaluation evaluation) {
        this.evaluations.add(evaluation);
        evaluation.setPost(this);
        return this;
    }

    public Post removeEvaluation(Evaluation evaluation) {
        this.evaluations.remove(evaluation);
        evaluation.setPost(null);
        return this;
    }

    public Set<IngredientOfDish> getIngredientOfDishes() {
        return this.ingredientOfDishes;
    }

    public void setIngredientOfDishes(Set<IngredientOfDish> ingredientOfDishes) {
        if (this.ingredientOfDishes != null) {
            this.ingredientOfDishes.forEach(i -> i.setPost(null));
        }
        if (ingredientOfDishes != null) {
            ingredientOfDishes.forEach(i -> i.setPost(this));
        }
        this.ingredientOfDishes = ingredientOfDishes;
    }

    public Post ingredientOfDishes(Set<IngredientOfDish> ingredientOfDishes) {
        this.setIngredientOfDishes(ingredientOfDishes);
        return this;
    }

    public Post addIngredientOfDish(IngredientOfDish ingredientOfDish) {
        this.ingredientOfDishes.add(ingredientOfDish);
        ingredientOfDish.setPost(this);
        return this;
    }

    public Post removeIngredientOfDish(IngredientOfDish ingredientOfDish) {
        this.ingredientOfDishes.remove(ingredientOfDish);
        ingredientOfDish.setPost(null);
        return this;
    }

    public Set<Post> getPosts() {
        return this.posts;
    }

    public void setPosts(Set<Post> posts) {
        if (this.posts != null) {
            this.posts.forEach(i -> i.setSupperComment(null));
        }
        if (posts != null) {
            posts.forEach(i -> i.setSupperComment(this));
        }
        this.posts = posts;
    }

    public Post posts(Set<Post> posts) {
        this.setPosts(posts);
        return this;
    }

    public Post addPost(Post post) {
        this.posts.add(post);
        post.setSupperComment(this);
        return this;
    }

    public Post removePost(Post post) {
        this.posts.remove(post);
        post.setSupperComment(null);
        return this;
    }

    public Customer getAuthor() {
        return this.author;
    }

    public void setAuthor(Customer customer) {
        this.author = customer;
    }

    public Post author(Customer customer) {
        this.setAuthor(customer);
        return this;
    }

    public Post getSupperComment() {
        return this.supperComment;
    }

    public void setSupperComment(Post post) {
        this.supperComment = post;
    }

    public Post supperComment(Post post) {
        this.setSupperComment(post);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Post)) {
            return false;
        }
        return id != null && id.equals(((Post) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Post{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
