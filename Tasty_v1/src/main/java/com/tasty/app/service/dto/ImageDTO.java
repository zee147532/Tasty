package com.tasty.app.service.dto;

import com.tasty.app.domain.enumeration.TypeOfImage;

public class ImageDTO {
    private Long id;
    private TypeOfImage type;
    private String uri;
    private Long postsId;
    private Long ingredientId;
    private String username;

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

    public Long getPostsId() {
        return postsId;
    }

    public void setPostsId(Long postsId) {
        this.postsId = postsId;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ImageDTO() {
    }

    public ImageDTO(Long id, TypeOfImage type, String uri, Long postsId, Long ingredientId, String username) {
        this.id = id;
        this.type = type;
        this.uri = uri;
        this.postsId = postsId;
        this.ingredientId = ingredientId;
        this.username = username;
    }
}
