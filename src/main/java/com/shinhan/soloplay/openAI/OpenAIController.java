package com.shinhan.soloplay.openAI;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.soloplay.subcategory.SubCategoriesDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/theme")
public class OpenAIController {

    final OpenAIService openAIService;

    @PostMapping("/analyze")
    public ThemeResponseDTO analyzeTheme(@RequestBody BucketListRequestDTO request) {
        return openAIService.getTheme(request);
    }
    
    @PostMapping("/recommend")
    public SubCategoriesDTO recommendSubcategories(@RequestBody ThemeResponseDTO themeResponse) {
        return openAIService.getRecommendedSubcategories(themeResponse.getTheme(), themeResponse.getDetails());
    }
}