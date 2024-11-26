package dev.mightyelephants.backend.controller;
import dev.mightyelephants.backend.model.ChatRequest;
import dev.mightyelephants.backend.model.ChatResponse;
import dev.mightyelephants.backend.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private ChatService chatService;
    @PostMapping()
    public ChatResponse sendMessage(@RequestBody ChatRequest chatRequest) {
        return chatService.getResponseFromLLM(chatRequest.getMessage());
    }
}
