package com.tasty.app.service.dto;

import java.util.List;

public class CommentDTO {
    private Long id;
    private String comment;
    private Long postsId;
    private List<SubComment> subCommentList;

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

    public Long getPostsId() {
        return postsId;
    }

    public void setPostsId(Long postsId) {
        this.postsId = postsId;
    }

    public List<SubComment> getSubCommentList() {
        return subCommentList;
    }

    public void setSubCommentList(List<SubComment> subCommentList) {
        this.subCommentList = subCommentList;
    }

    public CommentDTO() {
    }

    public CommentDTO(Long id, String comment, Long postsId, List<SubComment> subCommentList) {
        this.id = id;
        this.comment = comment;
        this.postsId = postsId;
        this.subCommentList = subCommentList;
    }
}
