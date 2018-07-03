package com.example.repository;

import com.example.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("PostRepository")
public interface PostRepository extends JpaRepository<Post,Long> {
    Post findById(Integer id);
}
