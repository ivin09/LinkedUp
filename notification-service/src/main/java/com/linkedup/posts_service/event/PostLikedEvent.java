package com.linkedup.posts_service.event;

import lombok.Builder;
import lombok.Data;

@Data
public class PostLikedEvent {
    public Long postId;
    public Long creatorId;
    public Long likedByUserId;
}
