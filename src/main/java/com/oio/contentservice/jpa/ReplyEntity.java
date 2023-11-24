package com.oio.contentservice.jpa;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "reply")
@SequenceGenerator(
        name = "SEQ_GENERATOR",
        sequenceName = "REVIEW_SEQ",
        allocationSize = 1
)
public class ReplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long replyId;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "postId")
    private PostEntity post;

}
