package com.shinhan.soloplay.openAI;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OpenAIResponseDTO {
    private List<Choice> choices;

    @Getter@Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Choice {
        private Message message;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Message {
            private String role;
            private String content;
        }
    }
}