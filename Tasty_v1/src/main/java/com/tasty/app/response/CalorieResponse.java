package com.tasty.app.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CalorieResponse {
    @JsonProperty("is_food")
    private Boolean isFood;

    @JsonProperty("results")
    private List<ApiResult> results;

    public Boolean isFood() {
        return isFood;
    }

    public void setIsFood(Boolean isFood) {
        this.isFood = isFood;
    }

    public List<ApiResult> getResults() {
        return results;
    }

    public void setResults(List<ApiResult> results) {
        this.results = results;
    }

    public CalorieResponse() {
    }

    public CalorieResponse(Boolean isFood, List<ApiResult> results) {
        this.isFood = isFood;
        this.results = results;
    }
}
