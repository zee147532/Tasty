package com.tasty.app.service.impl;

import com.tasty.app.domain.Comment;
import com.tasty.app.domain.Customer;
import com.tasty.app.domain.Post;
import com.tasty.app.repository.CommentRepository;
import com.tasty.app.repository.CustomerRepository;
import com.tasty.app.repository.PostRepository;
import com.tasty.app.request.CommentRequest;
import com.tasty.app.response.HttpResponse;
import com.tasty.app.response.SubCommentResponse;
import com.tasty.app.service.CommentService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import com.tasty.app.service.dto.CommentResponse;
import com.tasty.app.service.dto.SubComment;
import com.tasty.app.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Comment}.
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CustomerRepository customerRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, CustomerRepository customerRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Comment save(Comment comment) {
        log.debug("Request to save Comment : {}", comment);
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Comment comment) {
        log.debug("Request to update Comment : {}", comment);
        return commentRepository.save(comment);
    }

    @Override
    public Optional<Comment> partialUpdate(Comment comment) {
        log.debug("Request to partially update Comment : {}", comment);

        return commentRepository
            .findById(comment.getId())
            .map(existingComment -> {
                if (comment.getIsSubComment() != null) {
                    existingComment.setIsSubComment(comment.getIsSubComment());
                }
                if (comment.getComment() != null) {
                    existingComment.setComment(comment.getComment());
                }

                return existingComment;
            })
            .map(commentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Comment> findAll(Pageable pageable) {
        log.debug("Request to get all Comments");
        return commentRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Comment> findOne(Long id) {
        log.debug("Request to get Comment : {}", id);
        return commentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Comment : {}", id);
        commentRepository.deleteById(id);
    }

    @Override
    public Map<String, Object> getByPosts(Long postsId) {
        List<Comment> commentList = commentRepository.findAllByIsSubCommentAndPost_Id(false, postsId);
        List<Comment> subCommentList = commentRepository.findAllBySupperCommentIn(commentList);
        List<CommentResponse> commentResponses = commentList.stream().map(c -> {
            String time = getTime(c.getCreatedTime());
            return new CommentResponse(
                c.getId(),
                c.getCustomer().getUsername(),
                "https://rvs-comment-module.vercel.app/Assets/User Avatar.png",
                c.getComment(),
                time
            );
        }).collect(Collectors.toList());
        List<SubCommentResponse> subCommentResponses = subCommentList.stream().map(c -> {
            String time = getTime(c.getCreatedTime());
            return new SubCommentResponse(
                c.getId(),
                c.getCustomer().getUsername(),
                "https://rvs-comment-module.vercel.app/Assets/User Avatar-1.png",
                c.getComment(),
                time,
                c.getSupperComment().getId()
            );
        }).collect(Collectors.toList());
        Map<String, Object> result = new HashMap<>();
        result.put("comments", commentResponses);
        result.put("subComments", subCommentResponses);
        return result;
    }

    @Override
    public String addComment(CommentRequest request) {
        Post post = postRepository.getReferenceById(request.getPostsId());
        if (Objects.isNull(post.getId())) {
            return "Fail.";
        }
        Comment supperComment = request.getSubComment() ? commentRepository.getReferenceById(request.getSupperCommentId()) : null;
        if (request.getSubComment()) {
            assert supperComment != null;
            if (Objects.isNull(supperComment.getId())) {
                return "Fail.";
            }
        }
        Comment comment = new Comment()
            .isSubComment(request.getSubComment())
            .comment(request.getComment())
            .post(post)
            .supperComment(supperComment);

        commentRepository.save(comment);

        return "Success.";
    }

    @Override
    public HttpResponse updateComment(CommentRequest request) {
        // TODO: Lấy username từ token
        String username = "tiennd";
        Customer customer = customerRepository.findByUsername(username);
        if (Objects.isNull(customer)) {
            throw new BadRequestAlertException("Không thể thêm đánh giá", "customer", "customernotfound");
        }
        Post post = postRepository.getReferenceById(request.getPostsId());
        if (Objects.isNull(post.getId())) {
            throw new BadRequestAlertException("Không thể thêm đánh giá", "posts", "postsnotfound");
        }
        Comment comment = new Comment();
        if (Objects.nonNull(request.getId())) {
            comment = commentRepository.findById(request.getId()).orElse(new Comment());
        }

        Comment superComment = Objects.isNull(request.getSupperCommentId())
            ? null : commentRepository.getReferenceById(request.getSupperCommentId());

        comment.setComment(request.getComment());
        comment.setIsSubComment(request.getSubComment());
        comment.setPost(post);
        comment.setCustomer(customer);
        comment.setSupperComment(superComment);
        commentRepository.save(comment);
        return new HttpResponse(200, "Success.");
    }

    @Override
    public String deleteComment(Long id) {
        Comment comment = commentRepository.getReferenceById(id);
        if (!comment.getIsSubComment()) {
            commentRepository.deleteAllBySupperComment_Id(id);
        }
        commentRepository.delete(comment);
        return null;
    }

    public String getTime(LocalDateTime time) {
        Long minutes = ChronoUnit.MINUTES.between(time, LocalDateTime.now());
        if (minutes < 1) {
            return "Vừa xong";
        } else if (minutes < 60) {
            return String.format("%d phút trước", minutes);
        } else if (minutes < 1440) {
            return String.format("%d giờ trước", minutes / 60);
        } else if (minutes < 43200) {
            return String.format("%d ngày trước", minutes / 1440);
        } else if (minutes < 518400) {
            return String.format("%d tháng trước", minutes / 43200);
        } else {
            return String.format("%d năm trước", minutes / 518400);
        }
    }
}
