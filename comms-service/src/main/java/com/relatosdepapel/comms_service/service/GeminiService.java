package com.relatosdepapel.comms_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    private final RestClient restClient;

    @Value("${app.gemini.api-key}")
    private String apiKey;

    @Value("${app.gemini.url}")
    private String geminiUrl;

    public GeminiService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public String generateResponse(String userMessage) {
        if (apiKey == null || apiKey.isBlank()) {
            return "Soy el agente virtual de Relatos de Papel. Ahora mismo Gemini no está configurado.";
        }

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of(
                                "parts", List.of(
                                        Map.of(
                                                "text",
                                                "Eres un agente de atención al cliente de una librería online llamada Relatos de Papel. Responde de forma amable y breve. Usuario: " + userMessage
                                        )
                                )
                        )
                )
        );

        try {
            Map response = restClient.post()
                    .uri(geminiUrl + "?key=" + apiKey)
                    .body(requestBody)
                    .retrieve()
                    .body(Map.class);

            return extractText(response);
        } catch (Exception e) {
            return "Lo siento, ahora mismo no puedo contactar con el agente virtual.";
        }
    }

    private String extractText(Map response) {
        try {
            List candidates = (List) response.get("candidates");
            Map candidate = (Map) candidates.getFirst();
            Map content = (Map) candidate.get("content");
            List parts = (List) content.get("parts");
            Map part = (Map) parts.getFirst();

            return part.get("text").toString();
        } catch (Exception e) {
            return "No he podido interpretar la respuesta del agente virtual.";
        }
    }
}
