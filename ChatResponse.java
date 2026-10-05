package com.ruralbanking.chatbot.dto;

import java.time.LocalDateTime;

public record ChatResponse(
        Long id,
        String language,
        String userMessage,
        String botResponse,
        LocalDateTime createdAt
) {}
