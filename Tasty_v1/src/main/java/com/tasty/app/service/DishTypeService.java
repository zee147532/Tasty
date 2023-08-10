package com.tasty.app.service;

import com.tasty.app.domain.DishType;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link DishType}.
 */
public interface DishTypeService {
    /**
     * Save a dishType.
     *
     * @param dishType the entity to save.
     * @return the persisted entity.
     */
    DishType save(DishType dishType);

    /**
     * Updates a dishType.
     *
     * @param dishType the entity to update.
     * @return the persisted entity.
     */
    DishType update(DishType dishType);

    /**
     * Partially updates a dishType.
     *
     * @param dishType the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DishType> partialUpdate(DishType dishType);

    /**
     * Get all the dishTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DishType> findAll(Pageable pageable);

    /**
     * Get the "id" dishType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DishType> findOne(Long id);

    /**
     * Delete the "id" dishType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
