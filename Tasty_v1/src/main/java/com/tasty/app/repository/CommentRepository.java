package com.tasty.app.repository;

import com.tasty.app.domain.Comment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Comment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByIsSubCommentAndPost_Id(Boolean isSubComment, Long postsId);

    List<Comment> findAllBySupperComment_Id(Long id);

    @Modifying
    void deleteAllBySupperComment_Id(Long id);

    List<Comment> findAllBySupperCommentIn(List<Comment> comments);
}
