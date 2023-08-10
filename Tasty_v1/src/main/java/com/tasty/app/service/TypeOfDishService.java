package com.tasty.app.service;

import com.tasty.app.domain.TypeOfDish;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link TypeOfDish}.
 */
public interface TypeOfDishService {
    /**
     * Save a typeOfDish.
     *
     * @param typeOfDish the entity to save.
     * @return the persisted entity.
     */
    TypeOfDish save(TypeOfDish typeOfDish);

    /**
     * Updates a typeOfDish.
     *
     * @param typeOfDish the entity to update.
     * @return the persisted entity.
     */
    TypeOfDish update(TypeOfDish typeOfDish);

    /**
     * Partially updates a typeOfDish.
     *
     * @param typeOfDish the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TypeOfDish> partialUpdate(TypeOfDish typeOfDish);

    /**
     * Get all the typeOfDishes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeOfDish> findAll(Pageable pageable);

    /**
     * Get the "id" typeOfDish.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeOfDish> findOne(Long id);

    /**
     * Delete the "id" typeOfDish.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
