package com.tasty.app.response;

public class SubCommentResponse {
    private Long id;
    private String username;
    private String avatarUrl;
    private String comment;
    private String time;
    private Long superComment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getSuperComment() {
        return superComment;
    }

    public void setSuperComment(Long superComment) {
        this.superComment = superComment;
    }

    public SubCommentResponse() {
    }

    public SubCommentResponse(Long id, String username, String avatarUrl, String comment, String time, Long superComment) {
        this.id = id;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.comment = comment;
        this.time = time;
        this.superComment = superComment;
    }
}
