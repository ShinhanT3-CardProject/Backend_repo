package com.shinhan.soloplay.openAI;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.shinhan.soloplay.subcategory.SubCategoriesDTO;

@Service
public class OpenAIService {

    private final WebClient webClient;

    // 미리 정의된 테마들 및 소분류들
    private final Map<String, List<String>> predefinedThemes = Map.of(
        "생활", List.of("편의점", "마트", "전통시장", "택시비", "병원", "세탁소", "약국", "헤어샵"),
        "쇼핑", List.of("마트", "전통시장", "서점", "가구점", "가전제품", "약국", "백화점", "의류/패션", "스포츠용품"),
        "외식/카페", List.of("카페", "베이커리", "한식", "일식", "중식", "양식", "패스트푸드", "술집"),
        "문화/교육", List.of("스터디카페", "서점", "노래방", "독서실", "문구점", "스포츠시설", "영화/공연", "취미/오락", "학원"),
        "여행/교통", List.of("편의점", "카페", "마트", "술집", "택시", "면세점", "숙소", "여행사", "주유소", "주차장")
    );

    public OpenAIService(@Value("${openai.api-key}") String apiKey) {
        this.webClient = WebClient.builder()
            .baseUrl("https://api.openai.com/v1")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
            .build();
    }

    public ThemeResponseDTO getTheme(BucketListRequestDTO request) {
        String prompt = "다음 버킷리스트 항목들을 분석하여 아래 주어진 테마들 중에서 가장 관련성이 높은 테마를 선택하고, 그 이유를 3가지 리스트 형식으로 이유만 간략하게 설명해주세요. 테마 선택 결과에 대해서는 무조건 가능한 테마들 중에서만 선택하여 다른 부가적인 말은 하지 말고 단어로만 알려주세요. 추천에 대한 이유는 전체적인 이유로 답변해주시고, 친근한 말투로 한국어로 답변해주세요.\n\n" +
                        "가능한 테마들: " + String.join(", ", predefinedThemes.keySet()) + "\n\n" +
                        "버킷리스트: " + String.join(", ", request.getBucketList());

        OpenAIResponseDTO response = webClient.post()
            .uri("/chat/completions")
            .bodyValue(createOpenAIRequest(prompt))
            .retrieve()
            .bodyToMono(OpenAIResponseDTO.class)
            .block();

        return extractThemeFromResponse(response);
    }

    public SubCategoriesDTO getRecommendedSubcategories(String theme, List<String> details) {
        // GPT에게 소분류를 선택하도록 요청하는 프롬프트 생성
        String prompt = "다음 테마와 세부 설명을 기반으로 해당 테마 내에서 가장 관련성이 높은 소분류 항목들을 정해진 목록 중에서만 5개 선택해서 추천해주세요. 소분류만 순수하게 단어 형태로 적어주세요.\n\n" +
                        "테마: " + theme + "\n" +
                        "세부 설명: " + String.join(", ", details) + "\n\n" +
                        "가능한 소분류 항목들: " + String.join(", ", predefinedThemes.get(theme));

        OpenAIResponseDTO response = webClient.post()
            .uri("/chat/completions")
            .bodyValue(createOpenAIRequest(prompt))
            .retrieve()
            .bodyToMono(OpenAIResponseDTO.class)
            .block();

        return extractSubcategoriesFromResponse(response);
    }

    private OpenAIRequestDTO createOpenAIRequest(String prompt) {
        OpenAIRequestDTO request = new OpenAIRequestDTO();
        request.setModel("gpt-4o-mini"); // 사용하고 있는 모델에 따라 변경
        request.setMessages(List.of(new OpenAIRequestDTO.Message("user", prompt)));
        return request;
    }

    private ThemeResponseDTO extractThemeFromResponse(OpenAIResponseDTO response) {
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
            System.out.println(details);

            return new ThemeResponseDTO(theme, details);
        }
        return new ThemeResponseDTO("알 수 없음", List.of("테마를 분석할 수 없습니다."));
    }

    private SubCategoriesDTO extractSubcategoriesFromResponse(OpenAIResponseDTO response) {
        if (response != null && !response.getChoices().isEmpty()) {
            String content = response.getChoices().get(0).getMessage().getContent().trim();
            
            // 각 소분류 항목에서 숫자와 마침표를 제거하고, 순수한 소분류 이름만 추출
            List<String> subcategories = List.of(content.split("\n")).stream()
                .map(subcategory -> subcategory.replaceAll("^\\d+\\.\\s*", "").replace("- ", "").trim())
                .collect(Collectors.toList());
            
            System.out.println(subcategories);
            return new SubCategoriesDTO(subcategories);
        }
        
        return new SubCategoriesDTO(List.of("소분류를 분석할 수 없습니다."));
    }
}
