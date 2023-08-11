package com.tasty.app.service.dto;

public class IngredientOfDishDTO {
    private Long id;
    private Long postsId;
    private Long ingredientId;
    private String unit;
    private Long quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public IngredientOfDishDTO() {
    }

    public IngredientOfDishDTO(Long id, Long postsId, Long ingredientId, String unit, Long quantity) {
        this.id = id;
        this.postsId = postsId;
        this.ingredientId = ingredientId;
        this.unit = unit;
        this.quantity = quantity;
    }
}
