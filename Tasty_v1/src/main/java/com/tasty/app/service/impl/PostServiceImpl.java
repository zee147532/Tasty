package com.tasty.app.service.impl;

import com.tasty.app.domain.Post;
import com.tasty.app.domain.StepToCook;
import com.tasty.app.repository.PostRepository;
import com.tasty.app.repository.StepToCookRepository;
import com.tasty.app.service.PostService;
import com.tasty.app.service.dto.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private StepToCookRepository stepToCookRepository;

    @Override
    public List<PostDTO> getPosts(String keyword, Pageable pageable) {
        List<Post> postList = postRepository.getAll(keyword, pageable);
        return postList.stream().map(p -> {
            List<StepToCook> steps = stepToCookRepository.findAllByPost(p);
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
