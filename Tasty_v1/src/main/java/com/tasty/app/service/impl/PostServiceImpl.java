package com.tasty.app.service.impl;

import com.tasty.app.domain.Post;
import com.tasty.app.domain.StepToCook;
import com.tasty.app.repository.PostRepository;
import com.tasty.app.repository.StepToCookRepository;
import com.tasty.app.service.PostService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tasty.app.service.dto.PostDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Post}.
 */
@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;

    @Autowired
    private StepToCookRepository stepToCookRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post save(Post post) {
        log.debug("Request to save Post : {}", post);
        return postRepository.save(post);
    }

    @Override
    public Post update(Post post) {
        log.debug("Request to update Post : {}", post);
        return postRepository.save(post);
    }

    @Override
    public Optional<Post> partialUpdate(Post post) {
        log.debug("Request to partially update Post : {}", post);

        return postRepository
            .findById(post.getId())
            .map(existingPost -> {
                if (post.getTitle() != null) {
                    existingPost.setTitle(post.getTitle());
                }
                if (post.getContent() != null) {
                    existingPost.setContent(post.getContent());
                }
                if (post.getDescription() != null) {
                    existingPost.setDescription(post.getDescription());
                }
                if (post.getStatus() != null) {
                    existingPost.setStatus(post.getStatus());
                }
                if (post.getCreatedDate() != null) {
                    existingPost.setCreatedDate(post.getCreatedDate());
                }

                return existingPost;
            })
            .map(postRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Post> findAll(Pageable pageable) {
        log.debug("Request to get all Posts");
        return postRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Post> findOne(Long id) {
        log.debug("Request to get Post : {}", id);
        return postRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Post : {}", id);
        postRepository.deleteById(id);
    }

    @Override
    public List<PostDTO> getPosts(String keyword, Pageable pageable) {
        List<Post> postList = postRepository.getAll(keyword, pageable);
        return postList.stream().map(p -> {
            List<StepToCook> steps = stepToCookRepository.findAllByPost_Id(p.getId());
            List<PostDTO.Step> stepList = steps.stream().map(s ->
                new PostDTO.Step(s.getId(), s.getContent(), s.getIndex())
            ).collect(Collectors.toList());
            return new PostDTO(p.getId(), p.getTitle(), p.getContent(), p.getDescription(), p.getStatus(), p.getCreatedDate(), stepList);
        }).collect(Collectors.toList());
    }

    @Override
    public Post createPost(PostDTO dto) {
        Post post = new Post().title(dto.getTitle())
            .content(dto.getContent())
            .description(dto.getDescription())
            .status(dto.getStatus())
            .createdDate(dto.getCreatedDate());

        postRepository.save(post);

        for (PostDTO.Step step : dto.getSteps()) {
            StepToCook stepToCook = new StepToCook().content(step.getContent())
                .index(step.getIndex());

            stepToCookRepository.save(stepToCook);
        }

        return post;
    }

    @Override
    public Post updatePost(PostDTO dto) {
        Post post = postRepository.getReferenceById(dto.getId());
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setDescription(dto.getDescription());
        post.setStatus(dto.getStatus());

        postRepository.save(post);
        stepToCookRepository.deleteAllByPost(post);

        for (PostDTO.Step step : dto.getSteps()) {
            StepToCook stepToCook = new StepToCook().content(step.getContent())
                .index(step.getIndex());

            stepToCookRepository.save(stepToCook);
        }
        return post;
    }

    @Override
    public String removePost(Long id) {
        Post post = postRepository.getReferenceById(id);
        stepToCookRepository.deleteAllByPost(post);
        postRepository.delete(post);
        return "Success.";
    }
}
