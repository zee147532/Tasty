package com.tasty.app.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultItem {
    @JsonProperty("group")
    private String group;

    @JsonProperty("name")
    private String name;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResultItem() {
    }

    public ResultItem(String group, String name) {
        this.group = group;
        this.name = name;
    }
}
