package com.tasty.app.request;

import org.springframework.web.multipart.MultipartFile;

public class PostsImageRequest {
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public PostsImageRequest() {
    }

    public PostsImageRequest(MultipartFile file) {
        this.file = file;
    }
}
