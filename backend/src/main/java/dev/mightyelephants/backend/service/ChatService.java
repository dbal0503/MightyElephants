package dev.mightyelephants.backend.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mightyelephants.backend.model.ChatResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import io.github.cdimascio.dotenv.Dotenv;


@Service
public class ChatService {
    static Dotenv dotenv = Dotenv.load();

    private static final String OPENAI_API_URL= dotenv.get("OPENAI_API_URL");
    private static final String API_KEY = dotenv.get("API_KEY");

    public ChatResponse getResponseFromLLM(String message) {
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("OpenAI API URL: " + OPENAI_API_URL);
        System.out.println("API Key: " + API_KEY);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        // Create a more structured JSON payload that matches OpenAI's API
        String jsonBody = String.format("{\"model\": \"gpt-3.5-turbo\", \"messages\": [{" +
                "\"role\": \"system\", \"content\": \"You are a highly detailed customer support assistant for Mighty Elephants Package Delivery Service. Mighty Elephants delivery service supports live tracking, A delivery Quote Estimator and a diverse payment process that supports Paypal and Credit. For getting a quote a customer will require the following information. Users Location, Sender's name, Destination, Recipients Name, and Packages dimensions and weight. For Live Tracking a  Please assist the user with any questions regarding the service. Dont write your response in markdown or have any lists just use plain text\"}," +
                "{\"role\": \"user\", \"content\": \"%s\"}]," +
                "\"max_tokens\": 150}", message);

        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                OPENAI_API_URL,
                HttpMethod.POST,
                entity,
                String.class
        );

        ChatResponse chatResponse = new ChatResponse();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response.getBody());
            String botReply = jsonNode.path("choices").get(0).path("message").path("content").asText();
            chatResponse.setReply(botReply);
        } catch (Exception e) {
            e.printStackTrace();
            chatResponse.setReply("Error processing request");
        }
        return chatResponse;
    }
}
