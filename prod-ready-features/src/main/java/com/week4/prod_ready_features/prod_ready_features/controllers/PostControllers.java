package com.week4.prod_ready_features.prod_ready_features.controllers;


import com.week4.prod_ready_features.prod_ready_features.dto.Posts.PostCreationDTO;
import com.week4.prod_ready_features.prod_ready_features.dto.Posts.PostResponseDTO;
import com.week4.prod_ready_features.prod_ready_features.services.Auth.PostSecurityService;
import com.week4.prod_ready_features.prod_ready_features.services.Posts.PostService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/posts")
public class PostControllers {

    private final PostService postService;
    private final PostSecurityService postSecurityService;

    public PostControllers(PostService postService, PostSecurityService postSecurityService) {
        this.postService = postService;
        this.postSecurityService = postSecurityService;
    }

    @GetMapping
    @Secured("ROLE_USER")
    public List<PostResponseDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping
    @Secured({"ROLE_CREATOR", "ROLE_ADMIN"})
    public PostResponseDTO createNewPost(@RequestBody PostCreationDTO postCreationDTO) {
        return postService.createPost(postCreationDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@postSecurityService.isOwnerOfPost(id)")
    public PostResponseDTO getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @PutMapping("/{postId}")
    public PostResponseDTO updatePost(@RequestBody PostCreationDTO inputPost, @PathVariable Long postId) {
        return postService.updatePost(inputPost, postId);
    }

}
