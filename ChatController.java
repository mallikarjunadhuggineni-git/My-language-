package com.ruralbanking.chatbot.controller;

import com.ruralbanking.chatbot.dto.ChatRequest;
import com.ruralbanking.chatbot.dto.ChatResponse;
import com.ruralbanking.chatbot.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:5173")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<ChatResponse> chat(@Valid @RequestBody ChatRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chatService.chat(request));
    }

    @GetMapping("/history")
    public ResponseEntity<List<ChatResponse>> history() {
        return ResponseEntity.ok(chatService.history());
    }

    @DeleteMapping("/history")
    public ResponseEntity<Map<String, String>> clearHistory() {
        chatService.clearHistory();
        return ResponseEntity.ok(Map.of("message", "Conversation history cleared"));
    }
}
