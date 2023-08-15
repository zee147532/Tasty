package com.tasty.app.service.dto;

public class IngredientDTO {
    private Long id;
    private String name;
    private String otherName;
    private Long typeId;

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

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public IngredientDTO() {
    }

    public IngredientDTO(Long id, String name, String otherName, Long typeId) {
        this.id = id;
        this.name = name;
        this.otherName = otherName;
        this.typeId = typeId;
    }
}
