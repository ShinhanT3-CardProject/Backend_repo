package com.shinhan.soloplay.theme;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenAIController {

    private final OpenAIService openAIService;

    public OpenAIController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/recommendations")
    public RecommendationResponse getRecommendations(@RequestBody RecommendationRequest request) {
        String bucketListString = String.join(", ", request.getBucketList());
        String recommendation = openAIService.getRecommendations(bucketListString);
        return new RecommendationResponse(recommendation);
    }

    public static class RecommendationRequest {
        private List<String> bucketList;

        public List<String> getBucketList() {
            return bucketList;
        }

        public void setBucketList(List<String> bucketList) {
            this.bucketList = bucketList;
        }
    }

    public static class RecommendationResponse {
        private String recommendations;

        public RecommendationResponse(String recommendations) {
            this.recommendations = recommendations;
        }

        public String getRecommendations() {
            return recommendations;
        }

        public void setRecommendations(String recommendations) {
            this.recommendations = recommendations;
        }
    }
}
