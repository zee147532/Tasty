package com.tasty.app.repository;

import com.tasty.app.domain.Post;
import com.tasty.app.repository.projection.PostsDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Post entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p " +
        "WHERE p.title LIKE CONCAT('%', :keyword, '%') " +
        "OR p.content LIKE CONCAT('%', :keyword, '%') " +
        "OR p.description LIKE CONCAT('%', :keyword, '%')")
    Page<Post> getAllPosts(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT p.id AS id, " +
        "   p.title AS title, " +
        "   p.description AS description, " +
        "   i.uri AS imageUrl, " +
        "   SUM(e.point) / COUNT(e) AS rating, " +
        "   COUNT(e) AS totalReviews " +
        "FROM Post p " +
        "LEFT JOIN Image i ON i.type = 'DISH' AND i.post.id = p.id " +
        "LEFT JOIN p.evaluations e " +
        "WHERE p.id = :id " +
        "GROUP BY p.id")
    PostsDetail getDetail(@Param("id") Long id);

    Long countAllByAuthor_Username(String username);

    List<Post> findAllByAuthor_Username(String username);
}
