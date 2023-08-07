package com.skysrd.marketboro.api.reward.controller;

import com.skysrd.marketboro.api.reward.domain.dto.RewardRequest;
import com.skysrd.marketboro.api.reward.service.RewardService;
import com.skysrd.marketboro.common.result.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reward")
@RequiredArgsConstructor
public class RewardController {

    private final RewardService rewardService;

    @GetMapping("/sum")
    public ResponseEntity<?> getRewardSummary(Long memberId) {
        return ResponseHandler.generate()
                .status(HttpStatus.OK)
                .data(rewardService.getRewardSummary(memberId))
                .build();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveReward(@RequestBody RewardRequest rewardRequest) {
        return ResponseHandler.generate()
                .status(HttpStatus.OK)
                .data(rewardService.saveReward(rewardRequest))
                .build();
    }

    @PostMapping("/spend")
    public ResponseEntity<?> spendReward(@RequestBody RewardRequest rewardRequest) {
        return ResponseHandler.generate()
                .status(HttpStatus.OK)
                .data(rewardService.spendReward(rewardRequest))
                .build();
    }

    @PostMapping("/rollback")
    public ResponseEntity<?> rollbackRewardSpend(Long rewardId) {
        return ResponseHandler.generate()
                .status(HttpStatus.OK)
                .data(rewardService.rollbackRewardSpend(rewardId))
                .build();
    }
}
