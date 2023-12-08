package com.oio.contentservice.controller;

import com.oio.contentservice.dto.PageRequestDto;
import com.oio.contentservice.dto.PageResponseDto;
import com.oio.contentservice.dto.PostDto;
import com.oio.contentservice.service.PostService;
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
import java.util.List;
import java.util.Map;

@RequestMapping("/")
@RestController
@RequiredArgsConstructor
@Log4j2
public class PostController {

    private final PostService postService;

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

    // 게시글 전체 조회
    @GetMapping("/posts/{category}")
    public ResponseEntity<PageResponseDto> getPosts(@PathVariable("category") String category, PageRequestDto pageRequestDto) {

//        List<PostDto> postList = postService.getPostAll();

        PageResponseDto<PostDto> pageResponseDto = postService.getPosts(pageRequestDto,category);

        return ResponseEntity.status(HttpStatus.OK).body(pageResponseDto);
    }


    // 내가 쓴 게시글 조회
    @GetMapping("/posts/member/{nickName}")
    public ResponseEntity<List<PostDto>> getPostsByNicKName(@PathVariable("nickName") String nickName) {

        List<PostDto> postList = postService.getPostAll(nickName);

        return ResponseEntity.status(HttpStatus.OK).body(postList);
    }

    // 게시글 조회
    @GetMapping("/post/{pno}/{nickName}")
    public ResponseEntity<Map<String, Object>> getPost(@PathVariable("pno") Long pno, @PathVariable("nickName") String nickName) {

        Map<String, Object> resultMap= postService.getPostById(pno, nickName);

        return ResponseEntity.status(HttpStatus.OK).body(resultMap);
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

        requestPostRemove.setPno(pno);

        postService.removePost(requestPostRemove);

        resultMap.put("pno", pno);

        return resultMap;
    }
}