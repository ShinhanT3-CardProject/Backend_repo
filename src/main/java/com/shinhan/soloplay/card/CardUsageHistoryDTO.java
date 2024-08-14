package com.shinhan.soloplay.card;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardUsageHistoryDTO {
    private Long usageId;
    private Timestamp transactionDate;
    private int amount; 
    private String cardNum;
}
