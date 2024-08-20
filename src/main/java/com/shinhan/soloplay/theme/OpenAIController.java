package com.shinhan.soloplay.theme;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/theme")
public class OpenAIController {

    private final OpenAIService openAIService;

    @Autowired
    public OpenAIController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/analyze")
    public ThemeResponseDTO analyzeTheme(@RequestBody BucketListRequestDTO request) {
        return openAIService.getTheme(request);
    }
    
    @PostMapping("/recommend")
    public SubCategoryDTO recommendSubcategories(@RequestBody ThemeResponseDTO themeResponse) {
        return openAIService.getRecommendedSubcategories(themeResponse.getTheme(), themeResponse.getDetails());
    }
}