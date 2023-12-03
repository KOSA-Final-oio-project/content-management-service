package com.oio.contentservice.jpa;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    private String nickName;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    @Builder.Default
    private int status = 0;

    @Column(nullable = false)
    @Builder.Default
    private int key = 0;

    @Column(nullable = true)
    private Long password;

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date createdAt;

    @OneToMany(mappedBy = "post",
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @Builder.Default
    private Set<PostImage> imageSet = new HashSet<>();

    public void change(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void changeStatus(int status) {
        this.status = status;
    }


    public void addImage(String uuid, String fileName) {

        PostImage postImage = PostImage.builder()
                .uuid(uuid)
                .fileName(fileName)
                .post(this)
                .ord(imageSet.size())
                .build();
        imageSet.add(postImage);
    }

    public void clearImages() {
        imageSet.forEach(postImage -> postImage.changePost(null));

        this.imageSet.clear();
    }

}
