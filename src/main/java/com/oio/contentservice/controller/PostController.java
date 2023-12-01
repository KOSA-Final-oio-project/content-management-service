package com.oio.contentservice.controller;

import com.oio.contentservice.dto.PageRequestDto;
import com.oio.contentservice.dto.PageResponseDto;
import com.oio.contentservice.dto.PostDto;
import com.oio.contentservice.service.PostService;
import com.oio.contentservice.vo.RequestPostModify;
import com.oio.contentservice.vo.ResponsePostModify;
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

@RequestMapping("/")
@RestController
@RequiredArgsConstructor
@Log4j2
public class PostController {

    private final PostService postService;

    @PostMapping (value = "/post/register/{nickName}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> register(@PathVariable("nickName") String nickName, @Valid @RequestBody PostDto postDto,
                                      BindingResult bindingResult) throws BindException {

        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }

        postDto.setNickName(nickName);

        Long pno = postService.register(postDto);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("게시글 등록 번호: ", pno);

        return resultMap;
    }

    @GetMapping ("/posts")
    public ResponseEntity<PageResponseDto> getPosts(PageRequestDto pageRequestDto){

//        List<PostDto> postList = postService.getPostAll();

        PageResponseDto<PostDto> pageResponseDto = postService.getPosts(pageRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(pageResponseDto);
    }

    @GetMapping("/post/{pno}")
    public ResponseEntity<PostDto> getPost(@PathVariable("pno") Long pno){

        PostDto postDto = postService.getPostById(pno);

        return ResponseEntity.status(HttpStatus.OK).body(postDto);
    }

    @PutMapping(value = "/post/{pno}" , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePostModify> modify(@PathVariable("pno") Long pno,
                                                     @Valid @RequestBody RequestPostModify RequestPostModify,
                                                     BindingResult bindingResult) throws BindException {

        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }

        RequestPostModify.setPno(pno);

        ResponsePostModify responseModify = postService.modifyPost(RequestPostModify);

        return ResponseEntity.status(HttpStatus.OK).body(responseModify);
    }

    @DeleteMapping("/post/{pno}")
    public Map<String, Long> remove(@PathVariable("pno") Long pno){

        postService.removePost(pno);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("pno", pno);

        return resultMap;
    }
}