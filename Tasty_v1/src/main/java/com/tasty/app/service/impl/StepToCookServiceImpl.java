package com.tasty.app.service.impl;

import com.tasty.app.domain.Post;
import com.tasty.app.domain.StepToCook;
import com.tasty.app.repository.PostRepository;
import com.tasty.app.repository.StepToCookRepository;
import com.tasty.app.service.StepToCookService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.tasty.app.service.dto.StepDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StepToCook}.
 */
@Service
@Transactional
public class StepToCookServiceImpl implements StepToCookService {

    private final Logger log = LoggerFactory.getLogger(StepToCookServiceImpl.class);

    private final StepToCookRepository stepToCookRepository;
    private final PostRepository postRepository;

    public StepToCookServiceImpl(StepToCookRepository stepToCookRepository, PostRepository postRepository) {
        this.stepToCookRepository = stepToCookRepository;
        this.postRepository = postRepository;
    }

    @Override
    public StepToCook save(StepToCook stepToCook) {
        log.debug("Request to save StepToCook : {}", stepToCook);
        return stepToCookRepository.save(stepToCook);
    }

    @Override
    public StepToCook update(StepToCook stepToCook) {
        log.debug("Request to update StepToCook : {}", stepToCook);
        return stepToCookRepository.save(stepToCook);
    }

    @Override
    public Optional<StepToCook> partialUpdate(StepToCook stepToCook) {
        log.debug("Request to partially update StepToCook : {}", stepToCook);

        return stepToCookRepository
            .findById(stepToCook.getId())
            .map(existingStepToCook -> {
                if (stepToCook.getContent() != null) {
                    existingStepToCook.setContent(stepToCook.getContent());
                }
                if (stepToCook.getIndex() != null) {
                    existingStepToCook.setIndex(stepToCook.getIndex());
                }

                return existingStepToCook;
            })
            .map(stepToCookRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StepToCook> findAll(Pageable pageable) {
        log.debug("Request to get all StepToCooks");
        return stepToCookRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StepToCook> findOne(Long id) {
        log.debug("Request to get StepToCook : {}", id);
        return stepToCookRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StepToCook : {}", id);
        stepToCookRepository.deleteById(id);
    }

    @Override
    public List<StepToCook> findByPost(Long postId) {
        return stepToCookRepository.findAllByPost_IdOrderByIndexAsc(postId);
    }

    @Override
    public String createStep(StepDTO dto) {
        Post post = postRepository.getReferenceById(dto.getPostId());
        if (Objects.isNull(post.getId())) {
            return "Fail.";
        }
        StepToCook step = new StepToCook()
            .content(dto.getContent())
            .index(dto.getIndex())
            .post(post);

        stepToCookRepository.save(step);
        return "Success.";
    }

    @Override
    public String updateStep(StepDTO dto) {
        Post post = postRepository.getReferenceById(dto.getPostId());
        if (Objects.isNull(post.getId())) {
            return "Fail.";
        }

        StepToCook step = stepToCookRepository.getReferenceById(dto.getId());
        step.content(dto.getContent())
            .index(dto.getIndex());

        stepToCookRepository.save(step);
        return "Success.";
    }

    @Override
    public String deleteStep(Long id) {
        stepToCookRepository.deleteById(id);
        return null;
    }
}
