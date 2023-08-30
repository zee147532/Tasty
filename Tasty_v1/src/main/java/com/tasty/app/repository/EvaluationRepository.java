package com.tasty.app.repository;

import com.tasty.app.domain.Evaluation;
import com.tasty.app.domain.Post;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Spring Data JPA repository for the Evaluation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findAllByPost_Id(Long postId);

    @Query("SELECT SUM(e.point) / COUNT(e) FROM Evaluation e " +
        "WHERE e.post.id = :postId")
    Double calculateByPost(Long postId);

    @Query("SELECT e FROM Evaluation e " +
        "WHERE e.post.id = :postsId " +
        "AND e.customer.username = :username")
    Evaluation getRate(@Param("postsId") Long postsId,
                       @Param("username") String username);

    @Modifying
    void deleteAllByPost(Post post);

    @Query("SELECT COUNT(e) AS totalReview, " +
        "SUM(e.point) AS totalPoint " +
        "FROM Evaluation e " +
        "JOIN e.post p " +
        "WHERE p.id = :id")
    Map<String, Object> getRate(@Param("id") Long postsId);
}
