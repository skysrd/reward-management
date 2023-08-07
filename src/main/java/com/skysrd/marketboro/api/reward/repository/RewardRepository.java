package com.skysrd.marketboro.api.reward.repository;

import com.skysrd.marketboro.api.member.domain.entity.Member;
import com.skysrd.marketboro.api.reward.domain.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RewardRepository extends JpaRepository<Reward, Long> , RewardCustomRepository{
    List<Reward> findByMember(Member member);
}
