package com.oio.contentservice.controller;

import com.oio.contentservice.dto.PostDto;
import com.oio.contentservice.service.PostService;
import com.oio.contentservice.vo.ResponseModify;
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

@RequestMapping("/")
@RestController
@RequiredArgsConstructor
@Log4j2
public class PostController {

    private final PostService postService;

    @PostMapping (value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> register(@Valid @RequestBody PostDto postDto,
                                      BindingResult bindingResult) throws BindException {

        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }

        Long pno = postService.register(postDto);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("게시글 등록 번호: ", pno);

        return resultMap;
    }

    @GetMapping ("/posts")
    public ResponseEntity<List<PostDto>> getPosts(){

        List<PostDto> postList = postService.getPostByAll();

        return ResponseEntity.status(HttpStatus.OK).body(postList);
    }

    @GetMapping("/{pno}")
    public ResponseEntity<PostDto> getPost(@PathVariable("pno") Long pno){

        PostDto postDto = postService.getPostById(pno);

        return ResponseEntity.status(HttpStatus.OK).body(postDto);
    }

    @PutMapping(value = "/{pno}" , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModify> modify(@PathVariable("pno") Long pno, @RequestBody PostDto postDto){

        postDto.setPno(pno);

        ResponseModify responseModify = postService.modifyPost(postDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseModify);
    }

    @DeleteMapping("/{pno}")
    public Map<String, Long> remove(@PathVariable("pno") Long pno){

        postService.removePost(pno);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("pno", pno);

        return resultMap;
    }












}
