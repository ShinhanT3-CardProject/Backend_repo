package com.shinhan.soloplay.openAI;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ThemeResponseDTO {
    private String theme;
    private List<String> details;
}