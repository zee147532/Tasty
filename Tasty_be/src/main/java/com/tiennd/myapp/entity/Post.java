package com.tiennd.myapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "post")
public class Post {
    @Id
    private Long id;

    private String title;

    private String content;

    private String description;

    private Boolean status;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author")
    private Customer author;
}
