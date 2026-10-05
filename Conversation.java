package com.ruralbanking.chatbot.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "conversations")
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String language;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String userMessage;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String botResponse;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Conversation() {
    }

    public Conversation(String language, String userMessage, String botResponse) {
        this.language = language;
        this.userMessage = userMessage;
        this.botResponse = botResponse;
        this.createdAt = LocalDateTime.now();
    }

    @PrePersist
    public void beforeCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public String getBotResponse() {
        return botResponse;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public void setBotResponse(String botResponse) {
        this.botResponse = botResponse;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
