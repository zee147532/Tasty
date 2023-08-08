package com.tasty.app.repository;

import com.tasty.app.domain.IngredientOfDish;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the IngredientOfDish entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IngredientOfDishRepository extends JpaRepository<IngredientOfDish, Long> {}
