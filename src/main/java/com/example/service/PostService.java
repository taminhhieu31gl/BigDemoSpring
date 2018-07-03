package com.example.service;

import com.example.model.Post;
import org.springframework.stereotype.Service;

@Service("PostService")
public interface PostService {
    void savePost(Post post);
}
