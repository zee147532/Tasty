package com.tasty.app.service.impl;

import com.tasty.app.domain.Comment;
import com.tasty.app.domain.Post;
import com.tasty.app.repository.CommentRepository;
import com.tasty.app.repository.PostRepository;
import com.tasty.app.request.CommentRequest;
import com.tasty.app.service.CommentService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tasty.app.service.dto.CommentDTO;
import com.tasty.app.service.dto.SubComment;
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

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
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
    public List<CommentDTO> getByPosts(Long postsId) {
        List<Comment> commentList = commentRepository.findAllByIsSubCommentAndPost_Id(false, postsId);
        return commentList.stream().map(c -> {
            List<Comment> subComments = commentRepository.findAllBySupperComment_Id(c.getId());
            return new CommentDTO(c.getId(),
                c.getComment(),
                c.getPost().getId(),
                subComments.stream().map(sc -> new SubComment(sc.getId(),
                    sc.getComment(),
                    sc.getSupperComment().getId())
                    ).collect(Collectors.toList()));
        }).collect(Collectors.toList());
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
    public String updateComment(CommentRequest request) {
        Comment comment = commentRepository.getReferenceById(request.getId());
        comment.setComment(request.getComment());
        commentRepository.save(comment);
        return "Success.";
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
}
