package com.tasty.app.service.dto;

public class FavoriteDTO {
    private Long id;
    private String customerUsername;
    private Long postsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public Long getPostsId() {
        return postsId;
    }

    public void setPostsId(Long postsId) {
        this.postsId = postsId;
    }

    public FavoriteDTO() {
    }

    public FavoriteDTO(Long id, String customerUsername, Long postsId) {
        this.id = id;
        this.customerUsername = customerUsername;
        this.postsId = postsId;
    }
}
