package com.shinhan.soloplay.theme;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenAIRequest {
    private String model;
    private List<Message> messages;

    @Getter
    @Setter
    public static class Message {
        private String role;
        private String content;

        // 기본 생성자
        public Message() {}

        // 필요한 경우, 역할과 내용을 초기화하는 생성자 추가 가능
        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }
}