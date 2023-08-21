package com.tasty.app.response;

public class StepResponse {
    private Long id;
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public StepResponse() {
    }

    public StepResponse(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}
