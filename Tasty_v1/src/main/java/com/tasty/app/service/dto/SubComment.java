package com.tasty.app.service.dto;

public class SubComment {
    private Long id;
    private String comment;
    private Long supperCommentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getSupperCommentId() {
        return supperCommentId;
    }

    public void setSupperCommentId(Long supperCommentId) {
        this.supperCommentId = supperCommentId;
    }

    public SubComment() {
    }

    public SubComment(Long id, String comment, Long supperCommentId) {
        this.id = id;
        this.comment = comment;
        this.supperCommentId = supperCommentId;
    }
}
