package com.oio.contentservice.controller;

import com.oio.contentservice.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/")
@RestController
@RequiredArgsConstructor
public class PostController {

    @GetMapping("test")
    public String test(){
        return "연결완료";
    }

    @PostMapping ("/register")
    public Map<String, String> register(@RequestBody PostDto postDto){



        Map<String, String> resultMap = new HashMap<>();

        resultMap.put("결과", "성공");

        return resultMap;
    }
}
