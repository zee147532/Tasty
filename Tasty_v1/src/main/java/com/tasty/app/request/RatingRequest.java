package com.tasty.app.request;

public class RatingRequest {
    private Long id;
    private Integer rate;
    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public RatingRequest() {
    }

    public RatingRequest(Long id, Integer rate, String comment) {
        this.id = id;
        this.rate = rate;
        this.comment = comment;
    }
}
