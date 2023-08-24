package com.tasty.app.response;

import com.tasty.app.domain.enumeration.Gender;

public class CustomerProfileResponse {
    private Long id;
    private String username;
    private String fullName;
    private Gender gender;
    private String description;
    private String imageUrl;
    private Long totalPosts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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

    public Long getTotalPosts() {
        return totalPosts;
    }

    public void setTotalPosts(Long totalPosts) {
        this.totalPosts = totalPosts;
    }

    public CustomerProfileResponse() {
    }

    public CustomerProfileResponse(Long id, String username, String fullName, Gender gender, String description, String imageUrl, Long totalPosts) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.gender = gender;
        this.description = description;
        this.imageUrl = imageUrl;
        this.totalPosts = totalPosts;
    }
}
