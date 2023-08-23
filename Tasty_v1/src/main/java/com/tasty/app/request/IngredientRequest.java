package com.tasty.app.request;

public class IngredientRequest {
    private Long id;
    private String name;
    private String unit;
    private Integer quantity;

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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public IngredientRequest() {
    }

    public IngredientRequest(Long id, String name, String unit, Integer quantity) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.quantity = quantity;
    }
}
