package com.relatosdepapel.comms_service.controller;

import com.relatosdepapel.comms_service.dto.request.ChatMessageRequest;
import com.relatosdepapel.comms_service.dto.response.ChatMessageResponse;
import com.relatosdepapel.comms_service.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public ChatMessageResponse sendMessage(ChatMessageRequest request) {
        return chatService.processMessage(request);
    }
}
