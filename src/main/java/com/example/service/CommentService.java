package com.example.service;

import com.example.model.Comment;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("CommentService")
public interface CommentService {
    void Save(Comment comment);
    void deleteComment(Comment comment);
    Optional<Comment> findCommentbyId(Long id);
}
