package com.oio.contentservice.controller;

import com.oio.contentservice.dto.ReplyDto;
import com.oio.contentservice.service.ReplyService;
import com.oio.contentservice.vo.RequestReplyModify;
import com.oio.contentservice.vo.ResponseReplyModify;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Log4j2
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping(value = "/reply/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Long>> register(@Valid @RequestBody ReplyDto replyDto,
                                                      BindingResult bindingResult) throws BindException {

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        Long rno = replyService.register(replyDto);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("댓글 등록 완료: ", rno);

        return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
    }

    @PutMapping(value = "/reply/{rno}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseReplyModify> modify(@PathVariable("rno") Long rno,
                                                      @Valid @RequestBody RequestReplyModify RequestReplyModify,
                                                      BindingResult bindingResult) throws BindException {

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        RequestReplyModify.setRno(rno);

        ResponseReplyModify responseReplyModify = replyService.modify(RequestReplyModify);

        return ResponseEntity.status(HttpStatus.OK).body(responseReplyModify);
    }

    @DeleteMapping("/reply/{rno}")
    public ResponseEntity<Map<String, Long>> remove(@PathVariable("rno") Long rno) {

        replyService.remove(rno);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("댓글 삭제 완료 : ", rno);

        return ResponseEntity.status(HttpStatus.OK).body(resultMap);
    }
    @GetMapping("/replise/{pno}")
    public ResponseEntity<List<ReplyDto>> getReplies(@PathVariable("pno") Long pno){

        List<ReplyDto> resultList = replyService.list(pno);

        return ResponseEntity.status(HttpStatus.OK).body(resultList);
    }








}
