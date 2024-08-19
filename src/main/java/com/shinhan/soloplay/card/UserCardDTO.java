package com.shinhan.soloplay.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCardDTO {
    private String cardNum;
    private Long cardId;
    private String userId;  
}
