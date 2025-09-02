package com.week4.prod_ready_features.prod_ready_features.services;

import com.week4.prod_ready_features.prod_ready_features.dto.Posts.PostCreationDTO;
import com.week4.prod_ready_features.prod_ready_features.dto.Posts.PostResponseDTO;

import java.util.List;

public interface PostService {

    List<PostResponseDTO> getAllPosts();

    PostResponseDTO createPost(PostCreationDTO postCreationDTO);

    PostResponseDTO getPostById(Long id);

    PostResponseDTO updatePost(PostCreationDTO inputPost, Long postId);

}
