package com.tasty.app.service;

import com.tasty.app.domain.Evaluation;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Evaluation}.
 */
public interface EvaluationService {
    /**
     * Save a evaluation.
     *
     * @param evaluation the entity to save.
     * @return the persisted entity.
     */
    Evaluation save(Evaluation evaluation);

    /**
     * Updates a evaluation.
     *
     * @param evaluation the entity to update.
     * @return the persisted entity.
     */
    Evaluation update(Evaluation evaluation);

    /**
     * Partially updates a evaluation.
     *
     * @param evaluation the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Evaluation> partialUpdate(Evaluation evaluation);

    /**
     * Get all the evaluations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Evaluation> findAll(Pageable pageable);

    /**
     * Get the "id" evaluation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Evaluation> findOne(Long id);

    /**
     * Delete the "id" evaluation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
