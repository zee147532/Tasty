package com.tasty.app.service.impl;

import com.tasty.app.domain.TypeOfDish;
import com.tasty.app.repository.TypeOfDishRepository;
import com.tasty.app.service.TypeOfDishService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TypeOfDish}.
 */
@Service
@Transactional
public class TypeOfDishServiceImpl implements TypeOfDishService {

    private final Logger log = LoggerFactory.getLogger(TypeOfDishServiceImpl.class);

    private final TypeOfDishRepository typeOfDishRepository;

    public TypeOfDishServiceImpl(TypeOfDishRepository typeOfDishRepository) {
        this.typeOfDishRepository = typeOfDishRepository;
    }

    @Override
    public TypeOfDish save(TypeOfDish typeOfDish) {
        log.debug("Request to save TypeOfDish : {}", typeOfDish);
        return typeOfDishRepository.save(typeOfDish);
    }

    @Override
    public TypeOfDish update(TypeOfDish typeOfDish) {
        log.debug("Request to update TypeOfDish : {}", typeOfDish);
        return typeOfDishRepository.save(typeOfDish);
    }

    @Override
    public Optional<TypeOfDish> partialUpdate(TypeOfDish typeOfDish) {
        log.debug("Request to partially update TypeOfDish : {}", typeOfDish);

        return typeOfDishRepository
            .findById(typeOfDish.getId())
            .map(existingTypeOfDish -> {
                return existingTypeOfDish;
            })
            .map(typeOfDishRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TypeOfDish> findAll(Pageable pageable) {
        log.debug("Request to get all TypeOfDishes");
        return typeOfDishRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TypeOfDish> findOne(Long id) {
        log.debug("Request to get TypeOfDish : {}", id);
        return typeOfDishRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeOfDish : {}", id);
        typeOfDishRepository.deleteById(id);
    }
}
