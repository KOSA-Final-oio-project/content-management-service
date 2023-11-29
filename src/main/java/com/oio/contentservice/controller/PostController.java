package com.oio.contentservice.controller;

import com.oio.contentservice.dto.PostDto;
import com.oio.contentservice.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
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

    @GetMapping(value ="/test")
    public String test(){
        return "연결완료";
    }

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
}
