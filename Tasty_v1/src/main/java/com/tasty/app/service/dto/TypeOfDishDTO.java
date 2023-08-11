package com.tasty.app.service.dto;

public class TypeOfDishDTO {
    private Long id;
    private Long typeId;
    private Long postsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getPostsId() {
        return postsId;
    }

    public void setPostsId(Long postsId) {
        this.postsId = postsId;
    }

    public TypeOfDishDTO() {
    }

    public TypeOfDishDTO(Long id, Long typeId, Long postsId) {
        this.id = id;
        this.typeId = typeId;
        this.postsId = postsId;
    }
}
