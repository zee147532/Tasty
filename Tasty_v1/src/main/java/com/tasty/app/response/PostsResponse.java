package com.tasty.app.response;

import java.util.List;

public class PostsResponse {
    private Long id;
    private String name;
    private List<String> cuisine;
    private String imageUrl;
    private Double rating;
    private Long totalReviews;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCuisine() {
        return cuisine;
    }

    public void setCuisine(List<String> cuisine) {
        this.cuisine = cuisine;
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

    public PostsResponse() {
    }

    public PostsResponse(Long id, String name, List<String> cuisine, String imageUrl, Double rating, Long totalReviews) {
        this.id = id;
        this.name = name;
        this.cuisine = cuisine;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.totalReviews = totalReviews;
    }
}
