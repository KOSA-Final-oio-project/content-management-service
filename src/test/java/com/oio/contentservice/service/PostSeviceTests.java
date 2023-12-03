package com.oio.contentservice.service;

import com.oio.contentservice.dto.PostDto;
import com.oio.contentservice.vo.RequestPostModify;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class PostSeviceTests {

    @Autowired
    private PostService postService;

    @Test
    public void testServiceRegister() {

        PostDto postDto = PostDto.builder()
                .title("test.....")
                .category("공지사항")
                .nickName("nickName")
                .content("content.....")
                .build();

        Long pno = postService.register(postDto);
    }

    @Test
    public void testRegisterWithImages() {
        log.info(postService.getClass().getName());

        PostDto postDto = PostDto.builder()
                .title("test.....")
                .category("공지사항")
                .nickName("nickName")
                .content("content.....")
                .key(0)
                .status(0)
                .build();

        postDto.setFileNames(
                Arrays.asList(
                        UUID.randomUUID() + "_aaa.jpg",
                        UUID.randomUUID() + "_bbb.jpg",
                        UUID.randomUUID() + "_ccc.jpg"
                )
        );

        Long pno = postService.register(postDto);

        log.info(pno);
    }

    @Test
    public void testReadAll(){
        Long pno = 101L;

        PostDto postDto = postService.getPostById(pno);

        log.info(postDto);

        for(String fileName : postDto.getFileNames()){
            log.info(fileName);
        }
    }

    @Test
    public void testModify(){

        RequestPostModify requestPostModify = RequestPostModify.builder()
                .pno(101L)
                .title("test.....")
                .content("content.....")
                .build();

        requestPostModify.setFileNames(Arrays.asList(UUID.randomUUID()+"_zzz.jpg"));

        postService.modifyPost(requestPostModify);
    }

}
