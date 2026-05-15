package com.linkedup.posts_service.repository;


import com.linkedup.posts_service.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Post, Long> {
}
