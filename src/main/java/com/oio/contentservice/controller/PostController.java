package com.oio.contentservice.controller;

import com.oio.contentservice.dto.PageRequestDto;
import com.oio.contentservice.dto.PageResponseDto;
import com.oio.contentservice.dto.PostDto;
import com.oio.contentservice.service.PostService;
import com.oio.contentservice.service.ReplyService;
import com.oio.contentservice.vo.RequestPostModify;
import com.oio.contentservice.vo.RequestPostRemove;
import com.oio.contentservice.vo.ResponsePostModify;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/")
@RestController
@RequiredArgsConstructor
@Log4j2
public class PostController {

    private final PostService postService;

    private final ReplyService replyService;

    @PostMapping(value = "/post/register/{nickName}")
    public Map<String, Long> register(@PathVariable("nickName") String nickName, @Valid PostDto postDto,
                                      BindingResult bindingResult) throws BindException {


        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        postDto.setNickName(nickName);

        Long pno = postService.register(postDto);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("게시글 등록 번호: ", pno);

        return resultMap;
    }

    @GetMapping("/posts")
    public ResponseEntity<PageResponseDto> getPosts(PageRequestDto pageRequestDto) {

//        List<PostDto> postList = postService.getPostAll();

        PageResponseDto<PostDto> pageResponseDto = postService.getPosts(pageRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(pageResponseDto);
    }

    @GetMapping("/post/{pno}")
    public ResponseEntity<PostDto> getPost(@PathVariable("pno") Long pno) {

        PostDto postDto = postService.getPostById(pno);

        return ResponseEntity.status(HttpStatus.OK).body(postDto);
    }

    @PutMapping(value = "/post/{pno}")
    public ResponseEntity<ResponsePostModify> modify(@PathVariable("pno") Long pno,
                                                     @Valid RequestPostModify RequestPostModify,
                                                     BindingResult bindingResult) throws BindException {

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        RequestPostModify.setPno(pno);

        ResponsePostModify responseModify = postService.modifyPost(RequestPostModify);

        return ResponseEntity.status(HttpStatus.OK).body(responseModify);
    }

    @DeleteMapping ("/post/{pno}")
    public Map<String, Long> remove(@PathVariable("pno") Long pno, RequestPostRemove requestPostRemove) {

        Map<String, Long> resultMap = new HashMap<>();

        if(replyService.list(pno) != null && replyService.list(pno).size() > 0) {

            resultMap.put("삭제 불가능 : ", pno);

            return resultMap;
        }

        requestPostRemove.setPno(pno);

        postService.removePost(requestPostRemove);

        resultMap.put("pno", pno);

        return resultMap;
    }
}