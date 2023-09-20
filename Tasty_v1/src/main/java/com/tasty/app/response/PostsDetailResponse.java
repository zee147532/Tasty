package com.tasty.app.response;

import java.util.List;

public class PostsDetailResponse {
    private Long id;
    private String title;
    private String author;
    private List<String> tags;
    private String description;
    private String imageUrl;
    private Double rating;
    private Long totalReviews;
    private List<StepResponse> steps;
    private List<IngredientResponse> ingredients;
    private Boolean favorite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Long getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(Long totalReviews) {
        this.totalReviews = totalReviews;
    }

    public List<StepResponse> getSteps() {
        return steps;
    }

    public void setSteps(List<StepResponse> steps) {
        this.steps = steps;
    }

    public List<IngredientResponse> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientResponse> ingredients) {
        this.ingredients = ingredients;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public PostsDetailResponse() {
    }

    public PostsDetailResponse(Long id, String title, String author, List<String> tags, String description, String imageUrl, Double rating, Long totalReviews, List<StepResponse> steps, List<IngredientResponse> ingredients, Boolean favorite) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.tags = tags;
        this.description = description;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.totalReviews = totalReviews;
        this.steps = steps;
        this.ingredients = ingredients;
        this.favorite = favorite;
    }
}
