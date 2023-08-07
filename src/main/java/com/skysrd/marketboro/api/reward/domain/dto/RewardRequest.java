package com.skysrd.marketboro.api.reward.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RewardRequest {

    private Long memberId;
    private Integer price;

    @Builder(builderClassName = "createBuilder", builderMethodName = "createBuilder")
    public RewardRequest(Long memberId, Integer price) {
        this.memberId = memberId;
        this.price = price;
    }
}
