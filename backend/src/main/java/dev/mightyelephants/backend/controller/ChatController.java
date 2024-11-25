package dev.mightyelephants.backend.controller;
import dev.mightyelephants.backend.model.ChatRequest;
import dev.mightyelephants.backend.model.ChatResponse;
import dev.mightyelephants.backend.service.ChatService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;
    @PostMapping("/sendMessage")
    public ChatResponse sendMessage(@RequestBody ChatRequest chatRequest) {
        return chatService.getResponseFromLLM(chatRequest.getMessage());
    }
}
