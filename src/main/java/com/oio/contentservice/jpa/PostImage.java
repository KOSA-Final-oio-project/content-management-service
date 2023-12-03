package com.oio.contentservice.jpa;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "post")
public class PostImage implements Comparable<PostImage> {

    @Id
    private String uuid;

    private String fileName;

    private int ord;

    @ManyToOne
    private PostEntity post;

    @Override
    public int compareTo(PostImage o) {
        return this.ord = o.ord;
    }
    public void changePost(PostEntity post){
        this.post = post;
    }
}
