package com.ruralbanking.chatbot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChatRequest(
        @NotBlank(message = "Language is required")
        String language,

        @NotBlank(message = "Message is required")
        @Size(max = 500, message = "Message must be 500 characters or less")
        String message
) {}
