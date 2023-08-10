package com.tasty.app.service;

import com.tasty.app.domain.StepToCook;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link StepToCook}.
 */
public interface StepToCookService {
    /**
     * Save a stepToCook.
     *
     * @param stepToCook the entity to save.
     * @return the persisted entity.
     */
    StepToCook save(StepToCook stepToCook);

    /**
     * Updates a stepToCook.
     *
     * @param stepToCook the entity to update.
     * @return the persisted entity.
     */
    StepToCook update(StepToCook stepToCook);

    /**
     * Partially updates a stepToCook.
     *
     * @param stepToCook the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StepToCook> partialUpdate(StepToCook stepToCook);

    /**
     * Get all the stepToCooks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StepToCook> findAll(Pageable pageable);

    /**
     * Get the "id" stepToCook.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StepToCook> findOne(Long id);

    /**
     * Delete the "id" stepToCook.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
