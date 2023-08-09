package com.skysrd.marketboro.api.history.repository;

import com.skysrd.marketboro.api.history.domain.entity.History;
import com.skysrd.marketboro.api.history.domain.enums.RewardType;
import com.skysrd.marketboro.api.member.domain.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {
    Page<History> findPageByRewardTypeAndMember(RewardType rewardType, Member member, Pageable pageable);
}
