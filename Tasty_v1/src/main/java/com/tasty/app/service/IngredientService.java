package com.tasty.app.service;

import com.tasty.app.domain.Ingredient;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Ingredient}.
 */
public interface IngredientService {
    /**
     * Save a ingredient.
     *
     * @param ingredient the entity to save.
     * @return the persisted entity.
     */
    Ingredient save(Ingredient ingredient);

    /**
     * Updates a ingredient.
     *
     * @param ingredient the entity to update.
     * @return the persisted entity.
     */
    Ingredient update(Ingredient ingredient);

    /**
     * Partially updates a ingredient.
     *
     * @param ingredient the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Ingredient> partialUpdate(Ingredient ingredient);

    /**
     * Get all the ingredients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Ingredient> findAll(Pageable pageable);

    /**
     * Get the "id" ingredient.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Ingredient> findOne(Long id);

    /**
     * Delete the "id" ingredient.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
