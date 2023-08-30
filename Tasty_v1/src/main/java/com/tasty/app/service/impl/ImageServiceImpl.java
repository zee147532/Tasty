package com.tasty.app.service.impl;

import com.tasty.app.domain.Image;
import com.tasty.app.repository.ImageRepository;
import com.tasty.app.repository.IngredientRepository;
import com.tasty.app.repository.PostRepository;
import com.tasty.app.service.ImageService;
import com.tasty.app.service.dto.ImageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public String createImage(ImageDTO dto) {
        Image image = new Image();
        image.setType(dto.getType());
        image.setUri(dto.getUri());
        image.setPost(postRepository.getReferenceById(dto.getPostsId()));
        image.setIngredient(ingredientRepository.getReferenceById(dto.getIngredientId()));
        imageRepository.save(image);

        return "success";
    }
}
