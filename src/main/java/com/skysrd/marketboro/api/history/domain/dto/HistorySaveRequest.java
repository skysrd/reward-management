package com.skysrd.marketboro.api.history.domain.dto;

import com.skysrd.marketboro.api.history.domain.enums.RewardType;
import com.skysrd.marketboro.api.member.domain.entity.Member;
import lombok.Builder;
import lombok.Data;

@Data
public class HistorySaveRequest {
    private Member member;
    private Integer price;
    private RewardType rewardType;

    @Builder(builderClassName = "createBuilder", builderMethodName = "createBuilder")
    public HistorySaveRequest(Member member, Integer price, RewardType rewardType) {
        this.member = member;
        this.price = price;
        this.rewardType = rewardType;
    }
}
