package com.tasty.app.service.dto;

import java.util.List;

public class CommentResponse {
    private Long id;
    private String username;
    private String avatarUrl;
    private String comment;
    private String time;

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

    public CommentResponse() {
    }

    public CommentResponse(Long id, String username, String avatarUrl, String comment, String time) {
        this.id = id;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.comment = comment;
        this.time = time;
    }
}
