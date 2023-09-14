package com.tasty.app.service.dto;

import java.time.LocalDate;
import java.util.List;

public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private String description;
    private Boolean status = true;
    private LocalDate createdDate = LocalDate.now();
    private List<Step> steps;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getStatus() {
        return status;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public PostDTO(Long id, String title, String content, String description, Boolean status, LocalDate createdDate, List<Step> steps) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.description = description;
        this.status = status;
        this.createdDate = createdDate;
        this.steps = steps;
    }

    public static class Step {
        private Long id;
        private String content;

        public Long getId() {
            return id;
        }

        public String getContent() {
            return content;
        }

        public Step() {
        }

        public Step(Long id, String content) {
            this.id = id;
            this.content = content;
        }
    }
}
