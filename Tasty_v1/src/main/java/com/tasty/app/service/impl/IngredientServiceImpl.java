package com.tasty.app.service.impl;

import com.tasty.app.domain.Ingredient;
import com.tasty.app.domain.IngredientOfDish;
import com.tasty.app.domain.IngredientType;
import com.tasty.app.repository.IngredientOfDishRepository;
import com.tasty.app.repository.IngredientRepository;
import com.tasty.app.repository.IngredientTypeRepository;
import com.tasty.app.service.IngredientService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.tasty.app.service.dto.IngredientDTO;
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
    private final IngredientTypeRepository ingredientTypeRepository;
    private final IngredientOfDishRepository ingredientOfDishRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, IngredientTypeRepository ingredientTypeRepository, IngredientOfDishRepository ingredientOfDishRepository) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientTypeRepository = ingredientTypeRepository;
        this.ingredientOfDishRepository = ingredientOfDishRepository;
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

    @Override
    public List<Ingredient> getAll() {
        return ingredientRepository.findAll();
    }

    @Override
    public String createIngredient(IngredientDTO dto) {
        IngredientType type = ingredientTypeRepository.getReferenceById(dto.getTypeId());
        if(Objects.isNull(type.getId())) {
            return "Fail.";
        }

        Ingredient ingredient = new Ingredient()
            .name(dto.getName())
            .otherName(dto.getOtherName())
            .type(type);
        ingredientRepository.save(ingredient);
        return "Success.";
    }

    @Override
    public String updateIngredient(IngredientDTO dto) {
        IngredientType type = ingredientTypeRepository.getReferenceById(dto.getTypeId());
        if(Objects.isNull(type.getId())) {
            return "Fail.";
        }

        Ingredient ingredient = ingredientRepository.getReferenceById(dto.getId());
        ingredient.setName(dto.getName());
        ingredient.setOtherName(dto.getOtherName());
        ingredient.setType(type);
        ingredientRepository.save(ingredient);

        return "Success.";
    }

    @Override
    public String removeIngredient(Long id) {
        List<IngredientOfDish> ingredientOfDishList = ingredientOfDishRepository.findAllByIngredient_Id(id);
        if(!ingredientOfDishList.isEmpty()) {
            return "Fail.";
        }
        ingredientRepository.deleteById(id);
        return "Success.";
    }
}
