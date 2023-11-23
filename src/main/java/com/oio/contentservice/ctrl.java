package com.oio.contentservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RestController
public class ctrl {

    @GetMapping("/home")
    String home() {
        return "home";
    }
}
