package com.skysrd.marketboro.api.history.domain.dto;

import com.skysrd.marketboro.api.history.domain.entity.History;
import com.skysrd.marketboro.api.history.domain.enums.RewardType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoryResponse {

    private Long id;
    private Integer price;
    private RewardType rewardType;
    private LocalDateTime createdDate;

    @Builder(builderClassName = "createBuilder", builderMethodName = "createBuilder")
    public HistoryResponse(Long id, Integer price, RewardType rewardType, LocalDateTime createdDate) {
        this.id = id;
        this.price = price;
        this.rewardType = rewardType;
        this.createdDate = createdDate;
    }

    public static HistoryResponse toResponse(History history) {
        return createBuilder()
                .id(history.getId())
                .price(history.getPrice())
                .rewardType(history.getRewardType())
                .createdDate(history.getCreatedDate())
                .build();
    }
}
