package com.oio.contentservice.jpa;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reply")
@ToString(exclude = "post")
@SequenceGenerator(
        name = "SEQ_GENERATOR",
        sequenceName = "REVIEW_SEQ",
        allocationSize = 1
)
public class ReplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long rno;

    @Column(nullable = false)
    private String replyText;

    @ManyToOne(fetch = FetchType.LAZY)
    private PostEntity post;

    private String replyer;

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date createdAt;

    public void changeText(String text) {
        this.replyText = text;
    }
}
