package com.tiennd.myapp.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "comment")
public class Comment {
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post")
    private Post post;

    @Column(name = "is_sub_comment")
    private Boolean isSubComment;

    @Column(name = "super_comment")
    private Boolean superComment;

    private String comment;
}
