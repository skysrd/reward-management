package com.skysrd.marketboro.api.reward.service;

import com.skysrd.marketboro.api.history.domain.dto.HistorySaveRequest;
import com.skysrd.marketboro.api.history.domain.enums.RewardType;
import com.skysrd.marketboro.api.history.service.HistoryService;
import com.skysrd.marketboro.api.member.domain.entity.Member;
import com.skysrd.marketboro.api.member.service.MemberService;
import com.skysrd.marketboro.api.reward.domain.entity.Reward;
import com.skysrd.marketboro.api.reward.domain.dto.RewardRequest;
import com.skysrd.marketboro.api.reward.domain.dto.RewardResponse;
import com.skysrd.marketboro.api.reward.repository.RewardRepository;
import com.skysrd.marketboro.common.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RewardService {

    private final RewardRepository rewardRepository;
    private final MemberService memberService;
    private final HistoryService historyService;

    public Integer getRewardSummary(Long memberId) {
        Member member = memberService.getMember(memberId);

        Integer result = rewardRepository.findBalanceSummaryByMember(member);

        if (result != null) {
            return result;
        }
        return 0;
    }

    @Transactional
    public RewardResponse saveReward(RewardRequest rewardRequest) {
        Member member = memberService.getMember(rewardRequest.getMemberId());

        Reward reward = Reward.createBuilder()
                .member(member)
                .initialPrice(rewardRequest.getPrice())
                .build();

        rewardRepository.save(reward);

        historyService.saveHistory(HistorySaveRequest.createBuilder()
                .member(member)
                .price(rewardRequest.getPrice())
                .rewardType(RewardType.SAVE)
                .build());

        return RewardResponse.toResponse(reward);
    }

    @Transactional
    public RewardResponse spendReward(RewardRequest rewardRequest) {
        if(getRewardSummary(rewardRequest.getMemberId()) < rewardRequest.getPrice()) {
            throw ApiException.builder()
                    .errorMessage("Reward is not enough")
                    .errorCode("REWARD_NOT_ENOUGH")
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        Member member = memberService.getMember(rewardRequest.getMemberId());

        List<Reward> availRewardList = rewardRepository.findGtZeroRewards(member);

        Integer targetPrice = rewardRequest.getPrice();

        for (int i = 0; i < availRewardList.size(); i++) {
            Reward reward = availRewardList.get(i);
            if(reward.getBalance() < targetPrice) {
                targetPrice -= reward.getBalance();
                reward.subtractBalance(reward.getBalance());
            }
            else {
                reward.subtractBalance(targetPrice);
                targetPrice-=targetPrice;
            }

            if(targetPrice==0) {
                break;
            }
        }

        Reward reward = Reward.createBuilder()
                .member(member)
                .initialPrice(rewardRequest.getPrice() * -1)
                .build();

        rewardRepository.save(reward);

        historyService.saveHistory(HistorySaveRequest.createBuilder()
                .member(member)
                .price(rewardRequest.getPrice())
                .rewardType(RewardType.SPEND)
                .build());

        return RewardResponse.toResponse(reward);
    }

    @Transactional
    public RewardResponse rollbackRewardSpend(Long rewardId) {
        Reward targetReward = rewardRepository.findById(rewardId)
                        .orElseThrow(() -> ApiException.builder()
                                .errorMessage("Reward Not Found")
                                .errorCode("REWARD_NOT_FOUND")
                                .status(HttpStatus.BAD_REQUEST)
                                .build());

        Member member = targetReward.getMember();

        Integer targetPrice = targetReward.getInitialPrice() * -1;

        List<Reward> availableRewardList = rewardRepository.findGoeZeroRewards(member);

        for (int i = 0; i < availableRewardList.size(); i++) {
            Reward reward = availableRewardList.get(i);
            Integer difference = reward.getInitialPrice() - reward.getBalance();
            if ((reward.getBalance() < reward.getInitialPrice()) && difference < targetPrice) {
                reward.addBalance(difference);
                targetPrice -= difference;
            } else if (difference >= targetPrice) {
                reward.addBalance(targetPrice);
                targetPrice -= targetPrice;
            }

            if (targetPrice == 0) {
                break;
            }
        }

        historyService.saveHistory(HistorySaveRequest.createBuilder()
                .member(member)
                .price(targetReward.getInitialPrice() * -1)
                .rewardType(RewardType.SAVE)
                .build());

        targetReward.setInitialPrice(0);

        return RewardResponse.toResponse(targetReward);
    }
}
