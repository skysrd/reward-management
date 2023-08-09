package com.skysrd.marketboro.api.reward.domain.dto;

import com.skysrd.marketboro.api.reward.domain.entity.Reward;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RewardResponse {

    private Long id;
    private Integer balance;
    private Integer initialPrice;
    private LocalDateTime createdDate;

    @Builder(builderClassName = "createBuilder", builderMethodName = "createBuilder")
    public RewardResponse(Long id, Integer balance, Integer initialPrice, LocalDateTime createdDate) {
        this.id = id;
        this.balance = balance;
        this.initialPrice = initialPrice;
        this.createdDate = createdDate;
    }

    public static RewardResponse toResponse(Reward reward) {
        return createBuilder()
                .id(reward.getId())
                .balance(reward.getBalance())
                .initialPrice(reward.getInitialPrice())
                .createdDate(reward.getCreatedDate())
                .build();
    }
}
