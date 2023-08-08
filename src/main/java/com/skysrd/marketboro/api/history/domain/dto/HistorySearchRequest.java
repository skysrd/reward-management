package com.skysrd.marketboro.api.history.domain.dto;

import com.skysrd.marketboro.api.history.domain.enums.RewardType;
import com.skysrd.marketboro.common.dto.SearchDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class HistorySearchRequest extends SearchDto {
    private Long memberId;
    private RewardType rewardType;

    @Builder(builderClassName = "createBuilder", builderMethodName = "createBuilder")
    public HistorySearchRequest(Integer limit, Integer offset, String orderBy, Sort.Direction direction, Long memberId, RewardType rewardType) {
        super(limit, offset, orderBy, direction);
        this.memberId = memberId;
        this.rewardType = rewardType;
    }
}
