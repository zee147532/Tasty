package com.tasty.app.service.impl;

import com.tasty.app.domain.Ingredient;
import com.tasty.app.repository.IngredientRepository;
import com.tasty.app.service.IngredientService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Ingredient}.
 */
@Service
@Transactional
public class IngredientServiceImpl implements IngredientService {

    private final Logger log = LoggerFactory.getLogger(IngredientServiceImpl.class);

    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        log.debug("Request to save Ingredient : {}", ingredient);
        return ingredientRepository.save(ingredient);
    }

    @Override
    public Ingredient update(Ingredient ingredient) {
        log.debug("Request to update Ingredient : {}", ingredient);
        return ingredientRepository.save(ingredient);
    }

    @Override
    public Optional<Ingredient> partialUpdate(Ingredient ingredient) {
        log.debug("Request to partially update Ingredient : {}", ingredient);

        return ingredientRepository
            .findById(ingredient.getId())
            .map(existingIngredient -> {
                if (ingredient.getName() != null) {
                    existingIngredient.setName(ingredient.getName());
                }
                if (ingredient.getOtherName() != null) {
                    existingIngredient.setOtherName(ingredient.getOtherName());
                }

                return existingIngredient;
            })
            .map(ingredientRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Ingredient> findAll(Pageable pageable) {
        log.debug("Request to get all Ingredients");
        return ingredientRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Ingredient> findOne(Long id) {
        log.debug("Request to get Ingredient : {}", id);
        return ingredientRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ingredient : {}", id);
        ingredientRepository.deleteById(id);
    }
}
