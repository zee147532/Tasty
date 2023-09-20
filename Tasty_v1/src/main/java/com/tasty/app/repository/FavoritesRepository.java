package com.tasty.app.repository;

import com.tasty.app.domain.Customer;
import com.tasty.app.domain.Favorites;
import com.tasty.app.domain.Post;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Favorites entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FavoritesRepository extends JpaRepository<Favorites, Long> {
    @Modifying
    void deleteAllByCustomer_Username(String username);

    @Modifying
    void deleteAllByPost(Post post);

    @Query("SELECT p FROM Favorites f " +
        "JOIN f.post p " +
        "JOIN f.customer c " +
        "WHERE c.username = :username")
    List<Post> findAllByUsername(@Param("username") String username);

    Favorites findAllByPostAndCustomer(Post post, Customer customer);

    Favorites findAllByPost_IdAndCustomer_Username(Long postsId, String username);
}
