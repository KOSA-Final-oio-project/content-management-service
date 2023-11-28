package com.oio.contentservice.jpa;

import com.oio.contentservice.jpa.status.Status;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "post")
@SequenceGenerator(
        name = "SEQ_GENERATOR",
        sequenceName = "POST_SEQ",
        allocationSize = 1
)
public class PostEntity {

    @Id
    @Column(name = "postId")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long id;

    @Column(nullable = false)
    private String memberEmail;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private Status status;

//    @OneToMany(mappedBy = "post")
//    private List<ReplyEntity> replies;

}
