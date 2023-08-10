package com.tasty.app.service;

import com.tasty.app.domain.IngredientType;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link IngredientType}.
 */
public interface IngredientTypeService {
    /**
     * Save a ingredientType.
     *
     * @param ingredientType the entity to save.
     * @return the persisted entity.
     */
    IngredientType save(IngredientType ingredientType);

    /**
     * Updates a ingredientType.
     *
     * @param ingredientType the entity to update.
     * @return the persisted entity.
     */
    IngredientType update(IngredientType ingredientType);

    /**
     * Partially updates a ingredientType.
     *
     * @param ingredientType the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IngredientType> partialUpdate(IngredientType ingredientType);

    /**
     * Get all the ingredientTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IngredientType> findAll(Pageable pageable);

    /**
     * Get the "id" ingredientType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IngredientType> findOne(Long id);

    /**
     * Delete the "id" ingredientType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
