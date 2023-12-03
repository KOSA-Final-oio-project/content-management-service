package com.oio.contentservice.service;

import com.oio.contentservice.dto.ReplyDto;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class ReplyServiceTests {

    @Autowired
    private ReplyService replyService;


    @Test
    public void testRegister(){

        ReplyDto replyDto = ReplyDto.builder()
                .replyText("관리자 입니다다다다")
                .replyer("관리자")
                .pno(1L)
                .build();

        replyService.register(replyDto);

        log.info(replyDto);


    }
}
