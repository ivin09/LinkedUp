package com.linkedup.posts_service.services;

import com.linkedup.posts_service.entity.Post;
import com.linkedup.posts_service.entity.PostLike;
import com.linkedup.posts_service.exceptions.BadRequestException;
import com.linkedup.posts_service.exceptions.ResourceNotFoundException;
import com.linkedup.posts_service.repository.PostLikeRepository;
import com.linkedup.posts_service.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostsRepository postsRepository;

    public void likePost(Long postId, Long userId) {
        log.info("Attempting to like post with id {}", postId);
        boolean exists = postsRepository.existsById(postId);
        if(!exists) throw new ResourceNotFoundException("Post not found with id " + postId);

        boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        if (alreadyLiked) throw new BadRequestException("Cannot like same post again");

        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        postLikeRepository.save(postLike);
        log.info("Post with id: {} liked successfully", postId);
    }

    public void unLikePost(Long postId, Long userId) {
        log.info("Attempting to unlike post with id {}", postId);
        boolean exists = postsRepository.existsById(postId);
        if(!exists) throw new ResourceNotFoundException("Post not found with id " + postId);

        boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        if (!alreadyLiked) throw new BadRequestException("Cannot unlike same post which is not liked");

        postLikeRepository.deleteByUserIdAndPostId(userId, postId);

        log.info("Post with id: {} unliked successfully", postId);
    }
}
