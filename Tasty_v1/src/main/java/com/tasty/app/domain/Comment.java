package com.tasty.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Comment.
 */
@Entity
@Table(name = "comment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "is_sub_comment")
    private Boolean isSubComment;

    @Column(name = "comment")
    private String comment;

    @OneToMany(mappedBy = "supperComment")
    @JsonIgnoreProperties(value = { "comments", "post", "supperComment" }, allowSetters = true)
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "favorites", "stepToCooks", "comments", "evaluations", "ingredientOfDishes", "typeOfDishes", "author" },
        allowSetters = true
    )
    private Post post;

    @ManyToOne
    @JsonIgnoreProperties(value = { "comments", "post", "supperComment" }, allowSetters = true)
    private Comment supperComment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Comment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsSubComment() {
        return this.isSubComment;
    }

    public Comment isSubComment(Boolean isSubComment) {
        this.setIsSubComment(isSubComment);
        return this;
    }

    public void setIsSubComment(Boolean isSubComment) {
        this.isSubComment = isSubComment;
    }

    public String getComment() {
        return this.comment;
    }

    public Comment comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Set<Comment> getComments() {
        return this.comments;
    }

    public void setComments(Set<Comment> comments) {
        if (this.comments != null) {
            this.comments.forEach(i -> i.setSupperComment(null));
        }
        if (comments != null) {
            comments.forEach(i -> i.setSupperComment(this));
        }
        this.comments = comments;
    }

    public Comment comments(Set<Comment> comments) {
        this.setComments(comments);
        return this;
    }

    public Comment addComment(Comment comment) {
        this.comments.add(comment);
        comment.setSupperComment(this);
        return this;
    }

    public Comment removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setSupperComment(null);
        return this;
    }

    public Post getPost() {
        return this.post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Comment post(Post post) {
        this.setPost(post);
        return this;
    }

    public Comment getSupperComment() {
        return this.supperComment;
    }

    public void setSupperComment(Comment comment) {
        this.supperComment = comment;
    }

    public Comment supperComment(Comment comment) {
        this.setSupperComment(comment);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comment)) {
            return false;
        }
        return id != null && id.equals(((Comment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Comment{" +
            "id=" + getId() +
            ", isSubComment='" + getIsSubComment() + "'" +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
