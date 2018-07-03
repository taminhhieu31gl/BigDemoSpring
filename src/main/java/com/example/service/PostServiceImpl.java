package com.example.service;

import com.example.model.Post;
import com.example.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("PostServiceImpl")
public class PostServiceImpl implements PostService{
    @Autowired
    private PostRepository postRepository;

    @Override
    public void savePost(Post post){
        postRepository.save(post);
    }
}
