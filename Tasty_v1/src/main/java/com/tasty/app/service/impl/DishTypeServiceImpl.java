package com.tasty.app.service.impl;

import com.tasty.app.domain.DishType;
import com.tasty.app.repository.DishTypeRepository;
import com.tasty.app.service.DishTypeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DishType}.
 */
@Service
@Transactional
public class DishTypeServiceImpl implements DishTypeService {

    private final Logger log = LoggerFactory.getLogger(DishTypeServiceImpl.class);

    private final DishTypeRepository dishTypeRepository;

    public DishTypeServiceImpl(DishTypeRepository dishTypeRepository) {
        this.dishTypeRepository = dishTypeRepository;
    }

    @Override
    public DishType save(DishType dishType) {
        log.debug("Request to save DishType : {}", dishType);
        return dishTypeRepository.save(dishType);
    }

    @Override
    public DishType update(DishType dishType) {
        log.debug("Request to update DishType : {}", dishType);
        return dishTypeRepository.save(dishType);
    }

    @Override
    public Optional<DishType> partialUpdate(DishType dishType) {
        log.debug("Request to partially update DishType : {}", dishType);

        return dishTypeRepository
            .findById(dishType.getId())
            .map(existingDishType -> {
                if (dishType.getName() != null) {
                    existingDishType.setName(dishType.getName());
                }

                return existingDishType;
            })
            .map(dishTypeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DishType> findAll(Pageable pageable) {
        log.debug("Request to get all DishTypes");
        return dishTypeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DishType> findOne(Long id) {
        log.debug("Request to get DishType : {}", id);
        return dishTypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DishType : {}", id);
        dishTypeRepository.deleteById(id);
    }
}
