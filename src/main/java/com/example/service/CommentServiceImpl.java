package com.example.service;

import com.example.model.Comment;
import com.example.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("CommentServiceImpl")
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Override
    public void Save(Comment comment){
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Comment comment){
        commentRepository.delete(comment);
    }

    @Override
    public Optional<Comment> findCommentbyId(Long id){
        return commentRepository.findById(id);
    }

}
