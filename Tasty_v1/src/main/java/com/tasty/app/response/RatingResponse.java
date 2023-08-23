package com.tasty.app.response;

public class RatingResponse {
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

    public RatingResponse() {
    }

    public RatingResponse(Long id, Integer rate, String comment) {
        this.id = id;
        this.rate = rate;
        this.comment = comment;
    }
}
