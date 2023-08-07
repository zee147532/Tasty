package com.tiennd.myapp.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "step_to_cook")
public class StepToCook {
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post")
    private Post post;

    private String content;

    private Long index;
}
