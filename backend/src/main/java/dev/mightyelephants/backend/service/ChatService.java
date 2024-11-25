package dev.mightyelephants.backend.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mightyelephants.backend.model.ChatResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import io.github.cdimascio.dotenv.Dotenv;



public class ChatService {
    static Dotenv dotenv = Dotenv.load();

    private static final String OPENAI_API_URL= dotenv.get("OPENAI_API_URL");
    private static final String API_KEY = dotenv.get("API_KEY");

    public ChatResponse getResponseFromLLM(String message){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        assert API_KEY != null;
        headers.setBearerAuth(API_KEY);

        String jsonBody = String.format("{\"prompt\": \"%s\", \"max_tokens\": 150}", message);
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
        assert OPENAI_API_URL != null;
        ResponseEntity<String> response = restTemplate.exchange(
                OPENAI_API_URL, HttpMethod.POST, entity, String.class);
        ChatResponse chatResponse = new ChatResponse();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response.getBody());
            String botReply = jsonNode.get("choices").get(0).get("text").asText();
            chatResponse.setReply(botReply);
        } catch (Exception e) {
            e.printStackTrace();
            chatResponse.setReply("Error processing request");
        }
        return chatResponse;
    }
}
