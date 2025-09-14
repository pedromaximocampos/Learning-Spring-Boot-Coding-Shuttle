package com.week4.prod_ready_features.prod_ready_features.services.Auth;

import com.week4.prod_ready_features.prod_ready_features.dto.Posts.PostResponseDTO;
import com.week4.prod_ready_features.prod_ready_features.entities.UserEntity;
import com.week4.prod_ready_features.prod_ready_features.services.Posts.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSecurityService {

    private final PostService postService;

    public boolean isOwnerOfPost(Long postId){
        PostResponseDTO post  = postService.getPostById(postId);
        UserEntity user =  (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return post.getAuthor().getId().equals(user.getId());
    }
}
