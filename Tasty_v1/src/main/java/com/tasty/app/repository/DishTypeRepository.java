package com.tasty.app.repository;

import com.tasty.app.domain.DishType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the DishType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DishTypeRepository extends JpaRepository<DishType, Long> {
    @Query("SELECT t FROM DishType t " +
        "JOIN t.typeOfDishes tod " +
        "join tod.post p " +
        "WHERE p.id = :id")
    List<DishType> getAllType(@Param("id") Long postsId);

    @Query("SELECT dt FROM DishType dt " +
        "WHERE dt.name in :types")
    List<DishType> findByListName(@Param("types") List<String> types);

    @Query("SELECT dt.name FROM DishType dt " +
        "JOIN dt.typeOfDishes tod " +
        "JOIN tod.post p " +
        "where p.id = :id")
    List<String> getAllByPostId(@Param("id") Long id);
}
