package com.tasty.app.repository;

import com.tasty.app.domain.TypeOfDish;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TypeOfDish entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeOfDishRepository extends JpaRepository<TypeOfDish, Long> {
    @Modifying
    void deleteAllByPost_IdAndType_Id(Long postId, Long typeId);

    @Modifying
    void deleteAllByPost_Id(Long postId);
}
