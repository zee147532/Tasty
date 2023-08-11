package com.tasty.app.request;

public class CommentRequest {
    private Long id;
    private Boolean isSubComment;
    private String comment;
    private Long postsId;
    private Long supperCommentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getSubComment() {
        return isSubComment;
    }

    public void setSubComment(Boolean subComment) {
        isSubComment = subComment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getPostsId() {
        return postsId;
    }

    public void setPostsId(Long postsId) {
        this.postsId = postsId;
    }

    public Long getSupperCommentId() {
        return supperCommentId;
    }

    public void setSupperCommentId(Long supperCommentId) {
        this.supperCommentId = supperCommentId;
    }

    public CommentRequest() {
    }

    public CommentRequest(Long id, Boolean isSubComment, String comment, Long postsId, Long supperCommentId) {
        this.id = id;
        this.isSubComment = isSubComment;
        this.comment = comment;
        this.postsId = postsId;
        this.supperCommentId = supperCommentId;
    }
}
