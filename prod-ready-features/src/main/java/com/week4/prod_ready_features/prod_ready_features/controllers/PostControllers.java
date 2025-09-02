package com.week4.prod_ready_features.prod_ready_features.controllers;


import com.week4.prod_ready_features.prod_ready_features.dto.Posts.PostCreationDTO;
import com.week4.prod_ready_features.prod_ready_features.dto.Posts.PostResponseDTO;
import com.week4.prod_ready_features.prod_ready_features.services.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/posts")
public class PostControllers {

    private final PostService postService;

    public PostControllers(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostResponseDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping
    public PostResponseDTO createNewPost(@RequestBody PostCreationDTO postCreationDTO) {
        return postService.createPost(postCreationDTO);
    }

    @GetMapping("/{id}")
    public PostResponseDTO getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @PutMapping("/{postId}")
    public PostResponseDTO updatePost(@RequestBody PostCreationDTO inputPost, @PathVariable Long postId) {
        return postService.updatePost(inputPost, postId);
    }

}
