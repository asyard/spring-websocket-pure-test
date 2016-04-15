package com.asy.test.spring.websocket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.Random;

/**
 * Created by asy
 */
public class CustomHandler extends AbstractWebSocketHandler {

    private static final Gson gson = new GsonBuilder().create();

    private WebSocketSession webSocketSession;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("afterConnectionEstablished");
        webSocketSession = session;
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        System.out.println("handleMessage : " + message.getClass());

        JsonObject jsonMessage = gson.fromJson(((TextMessage)message).getPayload(), JsonObject.class);
        System.out.println(jsonMessage.get("status"));
        System.out.println(jsonMessage.get("value"));

        sendMessage(((TextMessage)message).getPayload());

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("handleTransportError");
        //TODO
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("afterConnectionClosed");
        //TODO
    }

    @Override
    public boolean supportsPartialMessages() {
        System.out.println("supportsPartialMessages false");
        return false;
    }


    public void sendMessage(String msg) throws IOException {
        Greeting greeting = new Greeting(msg);
        TextMessage textMessage = new TextMessage(gson.toJson(greeting));
		webSocketSession.sendMessage(textMessage);
    }

}
