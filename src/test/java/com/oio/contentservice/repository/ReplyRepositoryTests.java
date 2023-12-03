package com.oio.contentservice.repository;

import com.oio.contentservice.jpa.PostEntity;
import com.oio.contentservice.jpa.ReplyEntity;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void testRegister(){

        Long pno = 1L;

        PostEntity postEntity = PostEntity.builder().pno(pno).build();


        ReplyEntity replyEntity = ReplyEntity.builder()
                .replyText("관리자 입니다.")
                .post(postEntity)
                .replyer("관리자")
                .build();

        replyRepository.save(replyEntity);

        log.info(replyEntity);


    }


}
