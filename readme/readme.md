# 接口规范说明

## #Level 1

#### RoomWebSocket类

```java
    @OnMessage
    public void onMessage(String message, Session sesssion){
        Map messageMap = JsonUtil.readValueToMap(message);


    }

message内容:
message={"message":"",
         "type":"all"/"someone",
         "target":["uid"]}
//单独发给某人的前端部分没有写完x
```

说明:

前端页面:

注册：http://localhost:8099/chatroom/user/register

登录：http://localhost:8099/chatroom/user/login

聊天室：http://localhost:8099/chatroom/room

要先登录才能进入聊天室



Sql说明：

表放在readme文件夹里面，请在application.yml里面修改连接配置