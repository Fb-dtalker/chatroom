package com.fb.chatroom.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * @author FB
 * @Description 对应sql表的用户entity接口类
 */
@EntityScan
@Data
@Getter
@Setter
public class UserEntity {

    private String uid;
    private String userName;
    private String passWord;

}
