package com.tasty.app.service.impl;

import com.tasty.app.domain.IngredientOfDish;
import com.tasty.app.repository.IngredientOfDishRepository;
import com.tasty.app.service.IngredientOfDishService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link IngredientOfDish}.
 */
@Service
@Transactional
public class IngredientOfDishServiceImpl implements IngredientOfDishService {

    private final Logger log = LoggerFactory.getLogger(IngredientOfDishServiceImpl.class);

    private final IngredientOfDishRepository ingredientOfDishRepository;

    public IngredientOfDishServiceImpl(IngredientOfDishRepository ingredientOfDishRepository) {
        this.ingredientOfDishRepository = ingredientOfDishRepository;
    }

    @Override
    public IngredientOfDish save(IngredientOfDish ingredientOfDish) {
        log.debug("Request to save IngredientOfDish : {}", ingredientOfDish);
        return ingredientOfDishRepository.save(ingredientOfDish);
    }

    @Override
    public IngredientOfDish update(IngredientOfDish ingredientOfDish) {
        log.debug("Request to update IngredientOfDish : {}", ingredientOfDish);
        return ingredientOfDishRepository.save(ingredientOfDish);
    }

    @Override
    public Optional<IngredientOfDish> partialUpdate(IngredientOfDish ingredientOfDish) {
        log.debug("Request to partially update IngredientOfDish : {}", ingredientOfDish);

        return ingredientOfDishRepository
            .findById(ingredientOfDish.getId())
            .map(existingIngredientOfDish -> {
                if (ingredientOfDish.getUnit() != null) {
                    existingIngredientOfDish.setUnit(ingredientOfDish.getUnit());
                }
                if (ingredientOfDish.getQuantity() != null) {
                    existingIngredientOfDish.setQuantity(ingredientOfDish.getQuantity());
                }

                return existingIngredientOfDish;
            })
            .map(ingredientOfDishRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IngredientOfDish> findAll(Pageable pageable) {
        log.debug("Request to get all IngredientOfDishes");
        return ingredientOfDishRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IngredientOfDish> findOne(Long id) {
        log.debug("Request to get IngredientOfDish : {}", id);
        return ingredientOfDishRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IngredientOfDish : {}", id);
        ingredientOfDishRepository.deleteById(id);
    }
}
