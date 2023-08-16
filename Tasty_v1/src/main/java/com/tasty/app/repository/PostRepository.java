package com.tasty.app.repository;

import com.tasty.app.domain.Post;
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
}
