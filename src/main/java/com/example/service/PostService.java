package com.example.service;

import com.example.model.Post;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("PostService")
public interface PostService {
    void savePost(Post post);
    void deletePost(Post post);
    Optional<Post> findPostById(Long id);
}
