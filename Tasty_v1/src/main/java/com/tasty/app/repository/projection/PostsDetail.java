package com.tasty.app.repository.projection;

public interface PostsDetail {
    Double getRating();

    Long getTotalReviews();

    Long getId();

    String getTitle();

    String getDescription();

    String getImageUrl();

    String getAuthor();
}
