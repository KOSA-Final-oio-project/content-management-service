package com.oio.contentservice.repository;

import com.oio.contentservice.jpa.PostEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostRepositoryTests {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void testRepoRegister(){
        PostEntity postEntity = PostEntity.builder()
                .title("title.....")
                .content("content....")
                .nickName("nickName")
                .category("Q&A")
                .build();
        postRepository.save(postEntity);

    }
}
