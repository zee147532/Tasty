package com.tasty.app.service.impl;

import com.tasty.app.domain.Ingredient;
import com.tasty.app.domain.IngredientOfDish;
import com.tasty.app.domain.Post;
import com.tasty.app.repository.IngredientOfDishRepository;
import com.tasty.app.repository.IngredientRepository;
import com.tasty.app.repository.PostRepository;
import com.tasty.app.service.IngredientOfDishService;

import java.util.Objects;
import java.util.Optional;

import com.tasty.app.service.dto.IngredientOfDishDTO;
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
    private final IngredientRepository ingredientRepository;
    private final PostRepository postRepository;

    public IngredientOfDishServiceImpl(IngredientOfDishRepository ingredientOfDishRepository, IngredientRepository ingredientRepository, PostRepository postRepository) {
        this.ingredientOfDishRepository = ingredientOfDishRepository;
        this.ingredientRepository = ingredientRepository;
        this.postRepository = postRepository;
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

    @Override
    public String createIngredientOfDish(IngredientOfDishDTO dto) {
        Post post = postRepository.getReferenceById(dto.getPostsId());
        Ingredient ingredient = ingredientRepository.getReferenceById(dto.getIngredientId());
        if (Objects.isNull(post.getId()) || Objects.isNull(ingredient.getId())) {
            return "Fail.";
        }
        IngredientOfDish ingredientOfDish = new IngredientOfDish()
            .unit(dto.getUnit())
            .quantity(dto.getQuantity())
            .post(post)
            .ingredient(ingredient);
        ingredientOfDishRepository.save(ingredientOfDish);

        return "Success.";
    }

    @Override
    public String updateIngredientOfDish(IngredientOfDishDTO dto) {
        IngredientOfDish ingredientOfDish = ingredientOfDishRepository.getReferenceById(dto.getId());
        ingredientOfDish.setUnit(dto.getUnit());
        ingredientOfDish.setQuantity(dto.getQuantity());
        ingredientOfDishRepository.save(ingredientOfDish);

        return "Success.";
    }

    @Override
    public String removeIngredientOfDish(Long postsId, Long ingredientId) {
        ingredientOfDishRepository.deleteAllByPost_IdAndIngredient_Id(postsId, ingredientId);
        return "Success.";
    }
}
