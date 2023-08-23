package com.tasty.app.service.impl;

import com.tasty.app.domain.Customer;
import com.tasty.app.domain.Evaluation;
import com.tasty.app.domain.Post;
import com.tasty.app.repository.CustomerRepository;
import com.tasty.app.repository.EvaluationRepository;
import com.tasty.app.repository.PostRepository;
import com.tasty.app.response.RatingResponse;
import com.tasty.app.service.EvaluationService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.tasty.app.service.dto.EvaluationDTO;
import com.tasty.app.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Evaluation}.
 */
@Service
@Transactional
public class EvaluationServiceImpl implements EvaluationService {

    private final Logger log = LoggerFactory.getLogger(EvaluationServiceImpl.class);

    private final EvaluationRepository evaluationRepository;
    private final PostRepository postRepository;
    private final CustomerRepository customerRepository;

    public EvaluationServiceImpl(EvaluationRepository evaluationRepository, PostRepository postRepository, CustomerRepository customerRepository) {
        this.evaluationRepository = evaluationRepository;
        this.postRepository = postRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Evaluation save(Evaluation evaluation) {
        log.debug("Request to save Evaluation : {}", evaluation);
        return evaluationRepository.save(evaluation);
    }

    @Override
    public Evaluation update(Evaluation evaluation) {
        log.debug("Request to update Evaluation : {}", evaluation);
        return evaluationRepository.save(evaluation);
    }

    @Override
    public Optional<Evaluation> partialUpdate(Evaluation evaluation) {
        log.debug("Request to partially update Evaluation : {}", evaluation);

        return evaluationRepository
            .findById(evaluation.getId())
            .map(existingEvaluation -> {
                if (evaluation.getPoint() != null) {
                    existingEvaluation.setPoint(evaluation.getPoint());
                }
                if (evaluation.getComment() != null) {
                    existingEvaluation.setComment(evaluation.getComment());
                }

                return existingEvaluation;
            })
            .map(evaluationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Evaluation> findAll(Pageable pageable) {
        log.debug("Request to get all Evaluations");
        return evaluationRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Evaluation> findOne(Long id) {
        log.debug("Request to get Evaluation : {}", id);
        return evaluationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Evaluation : {}", id);
        evaluationRepository.deleteById(id);
    }

    @Override
    public List<Evaluation> findByPost(Long postId) {
        return evaluationRepository.findAllByPost_Id(postId);
    }

    @Override
    public RatingResponse createEvaluation(EvaluationDTO dto) {
        Post post = postRepository.getReferenceById(dto.getPostId());
        if (Objects.isNull(post.getId())) {
            throw new BadRequestAlertException("Không thể thêm đánh giá", "posts", "postsnotfound");
        }
        // TODO: Lấy username từ token
        String username = "tiennd";
        Customer customer = customerRepository.findByUsername(username);
         if (Objects.isNull(customer)) {
             throw new BadRequestAlertException("Không thể thêm đánh giá", "customer", "customernotfound");
         }

        Evaluation evaluation = new Evaluation()
            .point(dto.getPoint())
            .comment(dto.getComment())
            .post(post)
            .customer(customer);

        evaluation = evaluationRepository.save(evaluation);
        return new RatingResponse(evaluation.getId(), evaluation.getPoint(), evaluation.getComment());
    }

    @Override
    public RatingResponse updateEvaluation(EvaluationDTO dto) {
        Post post = postRepository.getReferenceById(dto.getPostId());
        if (Objects.isNull(post.getId())) {
            throw new BadRequestAlertException("Không thể thêm đánh giá", "posts", "postsnotfound");
        }

        // TODO: Lấy username từ token
        String username = "tiennd";
        Customer customer = customerRepository.findByUsername(username);
        if (Objects.isNull(customer)) {
            throw new BadRequestAlertException("Không thể thêm đánh giá", "customer", "customernotfound");
        }

        Evaluation evaluation = evaluationRepository.getReferenceById(dto.getId());
        if (Objects.isNull(evaluation)) {
            evaluation = new Evaluation();
        }
        evaluation.point(dto.getPoint())
            .comment(dto.getComment())
            .post(post)
            .customer(customer);

        evaluation = evaluationRepository.save(evaluation);
        return new RatingResponse(evaluation.getId(), evaluation.getPoint(), evaluation.getComment());
    }

    @Override
    public String deleteEvaluation(Long id) {
        evaluationRepository.deleteById(id);
        return "Success.";
    }

    @Override
    public Double calculateByPost(Long postId) {
        return evaluationRepository.calculateByPost(postId);
    }

    @Override
    public RatingResponse getRating(Long postsId) {
        // TODO: Lấy username từ token
        String username = "tiennd";

        Evaluation evaluation = evaluationRepository.getRate(postsId, username);
        return new RatingResponse(evaluation.getId(), evaluation.getPoint(), evaluation.getComment());
    }
}
