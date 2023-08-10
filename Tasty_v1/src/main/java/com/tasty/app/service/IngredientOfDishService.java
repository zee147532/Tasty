package com.tasty.app.service;

import com.tasty.app.domain.IngredientOfDish;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link IngredientOfDish}.
 */
public interface IngredientOfDishService {
    /**
     * Save a ingredientOfDish.
     *
     * @param ingredientOfDish the entity to save.
     * @return the persisted entity.
     */
    IngredientOfDish save(IngredientOfDish ingredientOfDish);

    /**
     * Updates a ingredientOfDish.
     *
     * @param ingredientOfDish the entity to update.
     * @return the persisted entity.
     */
    IngredientOfDish update(IngredientOfDish ingredientOfDish);

    /**
     * Partially updates a ingredientOfDish.
     *
     * @param ingredientOfDish the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IngredientOfDish> partialUpdate(IngredientOfDish ingredientOfDish);

    /**
     * Get all the ingredientOfDishes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IngredientOfDish> findAll(Pageable pageable);

    /**
     * Get the "id" ingredientOfDish.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IngredientOfDish> findOne(Long id);

    /**
     * Delete the "id" ingredientOfDish.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
