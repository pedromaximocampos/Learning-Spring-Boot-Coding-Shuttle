package com.week4.prod_ready_features.prod_ready_features.services.Posts;

import com.week4.prod_ready_features.prod_ready_features.dto.Posts.PostCreationDTO;
import com.week4.prod_ready_features.prod_ready_features.dto.Posts.PostResponseDTO;
import com.week4.prod_ready_features.prod_ready_features.entities.PostEntity;
import com.week4.prod_ready_features.prod_ready_features.exceptions.ResourceNotFoundException;
import com.week4.prod_ready_features.prod_ready_features.repositories.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<PostResponseDTO> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postEntity -> modelMapper.map(postEntity, PostResponseDTO.class))
                .toList();
    }

    @Override
    public PostResponseDTO createPost(PostCreationDTO postCreationDTO) {
        PostEntity postEntity = modelMapper.map(postCreationDTO, PostEntity.class);
        PostEntity savedPostEntity= postRepository.save(postEntity);

        return modelMapper.map(savedPostEntity, PostResponseDTO.class);
    }

    @Override
    public PostResponseDTO getPostById(Long id) {
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));

        return modelMapper.map(postEntity, PostResponseDTO.class);
    }

    @Override
    public PostResponseDTO updatePost(PostCreationDTO inputPost, Long postId) {
        PostEntity existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
        modelMapper.map(inputPost, existingPost);

        PostEntity updatedPost = postRepository.save(existingPost);

        return modelMapper.map(updatedPost, PostResponseDTO.class);
    }

}
