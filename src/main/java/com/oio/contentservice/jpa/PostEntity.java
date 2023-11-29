package com.oio.contentservice.jpa;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long pno;

    @Column(nullable = false)
    private String memberEmail;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    @Builder.Default
    private int status = 0;

    @Column(nullable = true)
    private Long password;

    @Column(nullable = false,updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    public void change(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
