package com.tasty.app.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ApiResult {
    @JsonProperty("group")
    private String group;

    @JsonProperty("items")
    private List<ResultItem> items;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<ResultItem> getItems() {
        return items;
    }

    public void setItems(List<ResultItem> items) {
        this.items = items;
    }

    public ApiResult() {
    }

    public ApiResult(String group, List<ResultItem> items) {
        this.group = group;
        this.items = items;
    }
}
