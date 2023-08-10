package com.tasty.app.service;

import com.tasty.app.domain.Post;
import com.tasty.app.service.dto.PostDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    List<PostDTO> getPosts(String keyword, Pageable pageable);

    Post createPost(PostDTO dto);

    Post updatePost(PostDTO dto);

    String removePost(Long id);
}
