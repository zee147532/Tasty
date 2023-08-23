package com.tasty.app.request;

import com.tasty.app.service.dto.PostDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PostsRequest {
    private Long id;
    private String title;
    private List<String> types;
    private String description;
    private MultipartFile image;
    private List<PostDTO.Step> steps;
    private List<IngredientRequest> ingredient;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public List<PostDTO.Step> getSteps() {
        return steps;
    }

    public void setSteps(List<PostDTO.Step> steps) {
        this.steps = steps;
    }

    public List<IngredientRequest> getIngredient() {
        return ingredient;
    }

    public void setIngredient(List<IngredientRequest> ingredient) {
        this.ingredient = ingredient;
    }

    public PostsRequest() {
    }

    public PostsRequest(Long id, String title, List<String> types, String description, MultipartFile image, List<PostDTO.Step> steps, List<IngredientRequest> ingredient) {
        this.id = id;
        this.title = title;
        this.types = types;
        this.description = description;
        this.image = image;
        this.steps = steps;
        this.ingredient = ingredient;
    }
}
