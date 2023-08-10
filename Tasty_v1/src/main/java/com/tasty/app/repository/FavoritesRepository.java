package com.tasty.app.repository;

import com.tasty.app.domain.Favorites;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Favorites entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FavoritesRepository extends JpaRepository<Favorites, Long> {}
