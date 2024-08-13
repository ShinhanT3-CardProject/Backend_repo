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
public class OpenAIResponse {

    private List<Choice> choices;

    @Getter
    @Setter
    public static class Choice {
        private Message message;

        @Getter
        @Setter
        public static class Message {
            private String content;

            // 기본 생성자
            public Message() {}

            // 필요한 경우, 내용을 초기화하는 생성자 추가 가능
            public Message(String content) {
                this.content = content;
            }
        }

        // 기본 생성자
        public Choice() {}

        // 필요한 경우, 메시지를 초기화하는 생성자 추가 가능
        public Choice(Message message) {
            this.message = message;
        }
    }
}