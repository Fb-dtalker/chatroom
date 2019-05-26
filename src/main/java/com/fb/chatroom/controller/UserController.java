package com.fb.chatroom.controller;

import com.fb.chatroom.entity.UserEntity;
import com.fb.chatroom.mapper.UserMapper;
import com.fb.chatroom.util.RandomGeter;
import com.fb.chatroom.util.TimeGeter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    UserMapper userMapper;

    @RequestMapping("/login")
    public String login(HttpSession session){
        if (!session.isNew()){
            if(session.getAttribute("uid")!=null){
                return "room.html";
            }
        }
        return "login.html";
    }

    @ResponseBody
    @RequestMapping("/dologin")
    public Map login(@RequestParam String username, @RequestParam String password, HttpSession session){
        Map returnMap = new HashMap();
        returnMap.put("userState","-1");
        UserEntity userEntity = userMapper.selectUserByUsernameAndPassword(username,password);
        if (userEntity!=null){
            session.setAttribute("uid",userEntity.getUid());
            session.setAttribute("username",userEntity.getUserName());
            returnMap.put("userState","1");
            returnMap.put("uid",userEntity.getUid());
        }
        return returnMap;
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        Map map = new HashMap();
        map.put("result","1");
        session.invalidate();
        return "login.html";
    }

    @RequestMapping("/register")
    public String register(){
        return "register.html";
    }

    @ResponseBody
    @RequestMapping("/doregister")
    public Map register(@RequestParam String username, @RequestParam String password){
        Map returnMap = new HashMap();

        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(username);
        userEntity.setPassWord(password);
        userEntity.setUid(TimeGeter.getTimeString("yyyyMMddhh")+ RandomGeter.getRandomNumber(4));

        userMapper.insertNewUserByMap(userEntity);
        returnMap.put("uid",userEntity.getUid());
        return returnMap;
    }
}
