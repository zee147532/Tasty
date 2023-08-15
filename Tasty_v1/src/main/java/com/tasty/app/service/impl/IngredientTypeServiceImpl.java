package com.tasty.app.service.impl;

import com.tasty.app.domain.Ingredient;
import com.tasty.app.domain.IngredientType;
import com.tasty.app.repository.IngredientRepository;
import com.tasty.app.repository.IngredientTypeRepository;
import com.tasty.app.service.IngredientTypeService;

import java.util.List;
import java.util.Optional;

import com.tasty.app.service.dto.IngredientTypeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link IngredientType}.
 */
@Service
@Transactional
public class IngredientTypeServiceImpl implements IngredientTypeService {

    private final Logger log = LoggerFactory.getLogger(IngredientTypeServiceImpl.class);

    private final IngredientTypeRepository ingredientTypeRepository;
    private final IngredientRepository ingredientRepository;

    public IngredientTypeServiceImpl(IngredientTypeRepository ingredientTypeRepository, IngredientRepository repository) {
        this.ingredientTypeRepository = ingredientTypeRepository;
        this.ingredientRepository = repository;
    }

    @Override
    public IngredientType save(IngredientType ingredientType) {
        log.debug("Request to save IngredientType : {}", ingredientType);
        return ingredientTypeRepository.save(ingredientType);
    }

    @Override
    public IngredientType update(IngredientType ingredientType) {
        log.debug("Request to update IngredientType : {}", ingredientType);
        return ingredientTypeRepository.save(ingredientType);
    }

    @Override
    public Optional<IngredientType> partialUpdate(IngredientType ingredientType) {
        log.debug("Request to partially update IngredientType : {}", ingredientType);

        return ingredientTypeRepository
            .findById(ingredientType.getId())
            .map(existingIngredientType -> {
                if (ingredientType.getName() != null) {
                    existingIngredientType.setName(ingredientType.getName());
                }

                return existingIngredientType;
            })
            .map(ingredientTypeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IngredientType> findAll(Pageable pageable) {
        log.debug("Request to get all IngredientTypes");
        return ingredientTypeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IngredientType> findOne(Long id) {
        log.debug("Request to get IngredientType : {}", id);
        return ingredientTypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IngredientType : {}", id);
        ingredientTypeRepository.deleteById(id);
    }

    @Override
    public List<IngredientType> getAll() {
        return ingredientTypeRepository.findAll();
    }

    @Override
    public String createType(IngredientTypeDTO dto) {
        IngredientType type = new IngredientType().name(dto.getName());
        ingredientTypeRepository.save(type);

        return "Success.";
    }

    @Override
    public String removeType(Long id) {
        List<Ingredient> ingredientList = ingredientRepository.findAllByType_Id(id);
        if(!ingredientList.isEmpty()) {
            return "Fail.";
        }
        ingredientTypeRepository.deleteById(id);
        return "Success";
    }
}
