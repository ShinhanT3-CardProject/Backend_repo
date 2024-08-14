package com.shinhan.soloplay.theme;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OpenAIService {

    private final WebClient webClient;

    // 미리 정의된 테마들
    private final List<String> predefinedThemes = List.of(
        "생활",
        "쇼핑",
        "외식/카페",
        "문화/교육",
        "여행/교통"
    );

    public OpenAIService(@Value("${openai.api-key}") String apiKey) {
        this.webClient = WebClient.builder()
            .baseUrl("https://api.openai.com/v1")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
            .build();
    }

    public ThemeResponse getTheme(BucketListRequest request) {
        String prompt = "다음 버킷리스트 항목들을 분석하여 아래 주어진 테마들 중에서 가장 관련성이 높은 테마를 선택하고, 그 이유를 설명해주세요. 테마 추천 결과는 가능한 테마들 중에 선별해서 무조건 테마만 단어 형태로 알려주고, 설명은 전체적인 이유로 답변해주세요. 한국어로 답변해주세요.\n\n" +
                        "가능한 테마들: " + String.join(", ", predefinedThemes) + "\n\n" +
                        "버킷리스트: " + String.join(", ", request.getBucketList());

        OpenAIResponse response = webClient.post()
            .uri("/chat/completions")
            .bodyValue(createOpenAIRequest(prompt))
            .retrieve()
            .bodyToMono(OpenAIResponse.class)
            .block();

        return extractThemeFromResponse(response);
    }

    private OpenAIRequest createOpenAIRequest(String prompt) {
        OpenAIRequest request = new OpenAIRequest();
        request.setModel("gpt-4o-mini"); // 사용하고 있는 모델에 따라 변경
        request.setMessages(List.of(new OpenAIRequest.Message("user", prompt)));
        return request;
    }

    private ThemeResponse extractThemeFromResponse(OpenAIResponse response) {
        if (response != null && !response.getChoices().isEmpty()) {
            String content = response.getChoices().get(0).getMessage().getContent().trim();

            // 응답 내용을 "\n\n"으로 분리하여 테마와 세부 설명을 나눕니다.
            String[] parts = content.split("\n\n", 2);
            String theme = "알 수 없음";
            List<String> details;

            if (parts.length > 1) {
                theme = parts[0].replace("테마: ", "").replace("추천 ", "").trim(); // "테마: " 및 "추천 " 접두사를 제거
                details = List.of(parts[1].split("\n"));
            } else {
                theme = parts[0].replace("테마: ", "").replace("추천 ", "").trim(); // "테마: " 및 "추천 " 접두사를 제거
                details = List.of("세부 설명이 제공되지 않았습니다.");
            }

            return new ThemeResponse(theme, details);
        }
        return new ThemeResponse("알 수 없음", List.of("테마를 분석할 수 없습니다."));
    }
}
