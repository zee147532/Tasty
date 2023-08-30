package com.tasty.app.web.rest;

import com.tasty.app.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ImageResource {
    private final ImageService imageService;

    public ImageResource(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/api/image/{imageName}")
    public ResponseEntity getImage(@PathVariable("imageName") String imageName) {
        return ResponseEntity.ok("");
    }
}
