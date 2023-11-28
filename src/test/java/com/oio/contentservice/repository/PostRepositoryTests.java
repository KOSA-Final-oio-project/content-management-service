package com.oio.contentservice.repository;

import com.oio.contentservice.jpa.PostEntity;
import com.oio.contentservice.jpa.status.Status;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class PostRepositoryTests {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void testRegister(){
        PostEntity postEntity = PostEntity.builder()
                .title("title.....")
                .memberEmail("12345@naver.com")
                .content("content....")
                .status(Status.QnA)
                .build();

        log.info(postEntity);
        postRepository.save(postEntity);

    }



}
