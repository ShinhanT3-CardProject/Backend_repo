package com.shinhan.soloplay.card;

import java.util.List;

public interface CardUsageHistoryService {
    List<CardUsageHistoryDTO> getCardUsageHistoryByCardNum(String cardNum);
}
