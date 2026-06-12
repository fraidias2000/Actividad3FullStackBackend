package com.relatosdepapel.comms_service.service;

import com.relatosdepapel.comms_service.dto.request.ChatMessageRequest;
import com.relatosdepapel.comms_service.dto.response.ChatMessageResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ChatService {

    private final GeminiService geminiService;

    public ChatService(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    public ChatMessageResponse processMessage(ChatMessageRequest request) {
        String agentResponse = geminiService.generateResponse(request.message());

        return new ChatMessageResponse(
                request.message(),
                agentResponse,
                LocalDateTime.now()
        );
    }
}
