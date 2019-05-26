package com.fb.chatroom.controller;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TestController {

    @RequestMapping("/test")
    public String test(){
        return "test page...";
    }

    @PostMapping(value = "/xss")
    public Object test(String name) {
        System.out.println(name);
        return name;
    }

    @PostMapping(value = "/json")
    public Object testJSON(@RequestBody String param) {
        return param;
    }

    @GetMapping(value = "/query")
    public Object testQuery(String q){
        return q;
    }

    @PostMapping(value = "test-json")
    public String testJson(String json){
        System.out.println(json);
        System.out.println(StringEscapeUtils.escapeHtml4(json));
        return StringEscapeUtils.escapeHtml4(json);
    }

    @GetMapping(value = "/test-test")
    public String test_(){
        System.out.println("<script>alter('1111');</script>");
        System.out.println(StringEscapeUtils.escapeHtml4("<script>alter('1111');</script>"));
        return "1";
    }
}
