package com.fb.chatroom.websocket;

import com.fb.chatroom.GetHttpSessionConfig;
import com.fb.chatroom.util.JsonUtil;
import com.fb.chatroom.util.TimeGeter;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint(value = "/room/websocket", configurator = GetHttpSessionConfig.class)
@Component
public class RoomWebsocket {
    private static int onlineCount = 0;
    private static HashMap<String,RoomWebsocket> roomOnlineUserMap = new HashMap<>();

    private String uid;
    private Session session;
    private HttpSession httpSession;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config){
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());

        this.session = session;
        this.httpSession = httpSession;

        this.uid = (String) httpSession.getAttribute("uid");
        roomOnlineUserMap.put(uid, this);
        addOnlineCount();

        Map roomStatement = new HashMap();
        roomStatement.put("onlineUserNum",RoomWebsocket.getOnlineCount());
        roomStatement.put("onlineUserList",RoomWebsocket.roomOnlineUserMap.keySet().toArray());
        roomStatement.put("message","-"+httpSession.getAttribute("username")+" enter the room-");
        roomStatement.put("extraKey", new String[]{"onlineUserNum","onlineUserList"});
        sendMessageToAll(roomStatement);

        return;
    }

    @OnClose
    public void onClose(){
        roomOnlineUserMap.remove(uid);
        Map roomStatement = new HashMap();
        roomStatement.put("type", "all");
        roomStatement.put("message","-"+httpSession.getAttribute("username")+" quit the room.-");
        subOnlineCount();

        sendMessageToAll(roomStatement);

    }

    @OnMessage
    public void onMessage(String message){
        Map messageMap = JsonUtil.readValueToMap(message);
        if (messageMap==null||messageMap.size()<=0){
            return; //空message，不执行
        }

        if("all".equals(messageMap.get("type"))){
            sendMessageToAll(messageMap);
        }else if ("someone".equals(messageMap.get("type"))){
            sendMessageToSomeones(messageMap);
        }

    }

    private Map buildMessageBasic(Map map){
        Map push = new HashMap();
        //利用StringEscapeUtils.escapeHtml4可以直接把字符串的js代码转安全格式，到时候发给浏览器，浏览器会自动解析
        push.put("message", StringEscapeUtils.escapeHtml4((String) map.get("message")));
        push.put("time", TimeGeter.getTimeStampInSecond());
        push.put("uid", uid);
        push.put("writer",httpSession.getAttribute("username"));
        push.put("type", map.get("type"));

        String[] extraKey = (String[]) map.get("extraKey");
        if (extraKey!=null){
            for(String key : extraKey){
                push.put(key, map.get(key));
            }
        }
        return push;
    }

    public void sendMessage(String message, Session receiverSession){
        try {
            receiverSession.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageToSomeone(String message, String uid){
        if (roomOnlineUserMap.get(uid)!=null){
            sendMessage(message, roomOnlineUserMap.get(uid).session);
        }
    }

    public void sendMessageToSomeones(Map messageMap){
        Map push = buildMessageBasic(messageMap);
        String[] receivers = (String[])messageMap.get("target");
        String message = JsonUtil.writeMapToJson(push);

        sendMessage(message, session);
        for (String key : receivers){
            sendMessageToSomeone(message, key);
        }
    }

    public void sendMessageToAll(Map messageMap){
        Map push = buildMessageBasic(messageMap);
        sendMessageToAll(JsonUtil.writeMapToJson(push));
    }

    public void sendMessageToAll(String message){
        for (RoomWebsocket item : roomOnlineUserMap.values()){
            try {
                item.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized int getOnlineCount(){
        return onlineCount;
    }

    public static synchronized void addOnlineCount(){
        RoomWebsocket.onlineCount++;
    }

    public static synchronized void subOnlineCount(){
        RoomWebsocket.onlineCount--;
    }
}
