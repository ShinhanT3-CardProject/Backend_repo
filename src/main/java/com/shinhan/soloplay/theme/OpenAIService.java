package com.shinhan.soloplay.theme;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class OpenAIService {
	
   
    //private String apiKey;
    
 
    
    private final WebClient webClient;

    public OpenAIService( @Value("${openai.api-key}") String apiKey ) {
        this.webClient = WebClient.builder()
            .baseUrl("https://api.openai.com/v1")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
            .build();
    }

    public String getRecommendations(String bucketList) {
        OpenAIRequest request = new OpenAIRequest();
        request.setModel("gpt-4o-mini");
        request.setMessages(List.of(
            new OpenAIRequest.Message("system", "You are a helpful assistant that recommends services based on user's bucket list."),
            new OpenAIRequest.Message("user", "Here is my bucket list: " + bucketList + ". What services or activities would you recommend?")
        ));

        Mono<OpenAIResponse> responseMono = webClient.post()
            .uri("/chat/completions")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(OpenAIResponse.class);

        OpenAIResponse response = responseMono.block();
        return response.getChoices().get(0).getMessage().getContent();
    }
}
