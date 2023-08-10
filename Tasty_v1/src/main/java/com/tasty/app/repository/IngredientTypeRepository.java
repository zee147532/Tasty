package com.tasty.app.repository;

import com.tasty.app.domain.IngredientType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the IngredientType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IngredientTypeRepository extends JpaRepository<IngredientType, Long> {}
