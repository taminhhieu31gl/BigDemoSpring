package com.example.service;

import com.example.model.Comment;
import com.example.model.Post;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("PostService")
public interface PostService {
    void savePost(Post post);
    void saveComment(Comment comment);
    void deletePost(Post post);
    Optional<Post> findPostById(Long id);
}
