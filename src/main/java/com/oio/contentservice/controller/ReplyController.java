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
import java.util.Map;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Log4j2
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping(value = "/reply/register/{nickName}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> register(@PathVariable("nickName") String nickName, @Valid @RequestBody ReplyDto replyDto,
                                                        BindingResult bindingResult) throws BindException {

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        Map<String, String> resultMap = new HashMap<>();

        if (!nickName.equals("관리자")) {
            resultMap.put("msg", "fail");
        }

        replyDto.setReplyer(nickName);

        replyService.register(replyDto);

        resultMap.put("msg", "success");

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

    @DeleteMapping("/reply/{rno}/{pno}")
    public ResponseEntity<Map<String, String>> remove(@PathVariable("rno") Long rno, @PathVariable("pno") Long pno) {

        replyService.remove(rno, pno);

        Map<String, String> resultMap = new HashMap<>();

        resultMap.put("msg", "success");

        return ResponseEntity.status(HttpStatus.OK).body(resultMap);
    }

    @GetMapping("/replies/{pno}/{nickName}")
    public ResponseEntity<Map<String, Object>> getReplies(@PathVariable("pno") Long pno, @PathVariable("nickName") String nickName) {

        Map<String, Object> resultMap = replyService.list(pno, nickName);

        return ResponseEntity.status(HttpStatus.OK).body(resultMap);
    }


}
