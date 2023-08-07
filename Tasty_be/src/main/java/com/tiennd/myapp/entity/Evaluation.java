package com.tiennd.myapp.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "evaluation")
public class Evaluation {
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post")
    private Post post;

    private Integer point;

    private String comment;
}
