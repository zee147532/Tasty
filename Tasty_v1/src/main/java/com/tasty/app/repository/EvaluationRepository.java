package com.tasty.app.repository;

import com.tasty.app.domain.Evaluation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
