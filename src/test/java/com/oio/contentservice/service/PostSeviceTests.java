package com.oio.contentservice.service;

import com.oio.contentservice.dto.PostDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostSeviceTests {

    @Autowired
    private PostService postService;

    @Test
    public void testServiceRegister(){

        PostDto postDto = PostDto.builder()
                .title("test.....")
                .category("공지사항")
                .memberEmail("oio@naver.com")
                .content("content.....")
                .build();

        Long pno = postService.register(postDto);
    }
}
