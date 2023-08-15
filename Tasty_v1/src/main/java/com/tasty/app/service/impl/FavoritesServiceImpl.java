package com.tasty.app.service.impl;

import com.tasty.app.domain.Customer;
import com.tasty.app.domain.Favorites;
import com.tasty.app.domain.Post;
import com.tasty.app.repository.CustomerRepository;
import com.tasty.app.repository.FavoritesRepository;
import com.tasty.app.repository.PostRepository;
import com.tasty.app.service.FavoritesService;

import java.util.Objects;
import java.util.Optional;

import com.tasty.app.service.dto.FavoriteDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Favorites}.
 */
@Service
@Transactional
public class FavoritesServiceImpl implements FavoritesService {

    private final Logger log = LoggerFactory.getLogger(FavoritesServiceImpl.class);

    private final FavoritesRepository favoritesRepository;
    private final CustomerRepository customerRepository;
    private final PostRepository postRepository;

    public FavoritesServiceImpl(FavoritesRepository favoritesRepository, CustomerRepository customerRepository, PostRepository postRepository) {
        this.favoritesRepository = favoritesRepository;
        this.customerRepository = customerRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Favorites save(Favorites favorites) {
        log.debug("Request to save Favorites : {}", favorites);
        return favoritesRepository.save(favorites);
    }

    @Override
    public Favorites update(Favorites favorites) {
        log.debug("Request to update Favorites : {}", favorites);
        return favoritesRepository.save(favorites);
    }

    @Override
    public Optional<Favorites> partialUpdate(Favorites favorites) {
        log.debug("Request to partially update Favorites : {}", favorites);

        return favoritesRepository
            .findById(favorites.getId())
            .map(existingFavorites -> {
                return existingFavorites;
            })
            .map(favoritesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Favorites> findAll(Pageable pageable) {
        log.debug("Request to get all Favorites");
        return favoritesRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Favorites> findOne(Long id) {
        log.debug("Request to get Favorites : {}", id);
        return favoritesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Favorites : {}", id);
        favoritesRepository.deleteById(id);
    }

    @Override
    public String createFavorite(FavoriteDTO dto) {
        Post post = postRepository.getReferenceById(dto.getPostsId());
        Customer customer = customerRepository.findByUsername(dto.getCustomerUsername());

        if(Objects.isNull(post.getId()) || Objects.isNull(customer.getUsername())) {
            return "Fail.";
        }

        Favorites favorites = new Favorites()
            .customer(customer)
            .post(post);
        favoritesRepository.save(favorites);

        return "Success.";
    }

    @Override
    public String deleteFavorite(Long id) {
        favoritesRepository.deleteById(id);
        return "Success.";
    }
}
