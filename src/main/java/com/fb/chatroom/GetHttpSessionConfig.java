package com.fb.chatroom;


import org.apache.catalina.session.StandardSessionFacade;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

@Configuration
public class GetHttpSessionConfig extends Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig sec,
                                HandshakeRequest request, HandshakeResponse response){
        StandardSessionFacade ssf = (StandardSessionFacade) request.getHttpSession();

        if (ssf != null) {
            HttpSession httpSession = (HttpSession) request.getHttpSession();
            sec.getUserProperties().put(HttpSession.class.getName() , httpSession);
            super.modifyHandshake(sec, request, response);
        }

    }
}
