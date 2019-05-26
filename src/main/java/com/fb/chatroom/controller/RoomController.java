package com.fb.chatroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/room")
public class RoomController {

    @RequestMapping("")
    public String enterRoom(HttpSession session){
        if (session.isNew()||session.getAttribute("uid") == null){
            return "login.html";
        }
        return "room.html";
    }



}
