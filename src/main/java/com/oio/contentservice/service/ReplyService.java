package com.oio.contentservice.service;

import com.oio.contentservice.dto.ReplyDto;
import com.oio.contentservice.vo.RequestReplyModify;
import com.oio.contentservice.vo.ResponseReplyModify;

import java.util.Map;

public interface ReplyService {

    Long register(ReplyDto replyDto);

    ResponseReplyModify modify(RequestReplyModify RequestReplyModify);

    Long remove(Long rno, Long pno);

    Map<String, Object> list(Long pno, String nickName);

}
