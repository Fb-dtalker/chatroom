package com.fb.chatroom.mapper;

import com.fb.chatroom.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;


/**
 * @author HASEE
 */
public interface UserMapper {
    @Insert("insert into user_table(uid,username,password) values (#{uid},#{userName},#{passWord})")
    int insertNewUserByMap(UserEntity user);

    @Select("select * from user_table where uid = #{uid}")
    UserEntity selectUserByUid(String uid);

    @Select("select * from user_table where username = #{userName} and password = #{passWord}")
    UserEntity selectUserByUsernameAndPassword(String userName, String passWord);

}
