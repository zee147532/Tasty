package com.tasty.app.service.dto;

public class StepDTO {
    private Long id;
    private String content;
    private Long index;

    public StepDTO() {
    }

    public StepDTO(Long id, String content, Long index, Long postId) {
        this.id = id;
        this.content = content;
        this.index = index;
        this.postId = postId;
    }

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

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    private Long postId;

}
