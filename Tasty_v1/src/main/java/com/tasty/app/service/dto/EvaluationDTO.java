package com.tasty.app.service.dto;

public class EvaluationDTO {
    private Long id;
    private Integer point;
    private String comment;
    private Long postId;

    public EvaluationDTO() {
    }

    public EvaluationDTO(Long id, Integer point, String comment, Long postId) {
        this.id = id;
        this.point = point;
        this.comment = comment;
        this.postId = postId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
