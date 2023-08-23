package com.tasty.app.service;

import com.tasty.app.domain.Post;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.tasty.app.request.PostsRequest;
import com.tasty.app.response.HttpResponse;
import com.tasty.app.response.PostsDetailResponse;
import com.tasty.app.response.PostsResponse;
import com.tasty.app.service.dto.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Post}.
 */
public interface PostService {
    /**
     * Save a post.
     *
     * @param post the entity to save.
     * @return the persisted entity.
     */
    Post save(Post post);

    /**
     * Updates a post.
     *
     * @param post the entity to update.
     * @return the persisted entity.
     */
    Post update(Post post);

    /**
     * Partially updates a post.
     *
     * @param post the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Post> partialUpdate(Post post);

    /**
     * Get all the posts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Post> findAll(Pageable pageable);

    /**
     * Get the "id" post.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Post> findOne(Long id);

    /**
     * Delete the "id" post.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Map<String, Object> getPosts(String keyword, Pageable pageable);

    Post createPost(PostsRequest request);

    Post updatePost(PostDTO dto);

    String removePost(Long id);

    PostsDetailResponse getDetail(Long id);
}
