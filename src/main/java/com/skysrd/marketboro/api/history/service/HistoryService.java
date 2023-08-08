package com.skysrd.marketboro.api.history.service;

import com.skysrd.marketboro.api.history.domain.dto.HistorySearchRequest;
import com.skysrd.marketboro.api.history.domain.dto.HistoryResponse;
import com.skysrd.marketboro.api.history.domain.dto.HistorySaveRequest;
import com.skysrd.marketboro.api.history.domain.entity.History;
import com.skysrd.marketboro.api.history.domain.enums.RewardType;
import com.skysrd.marketboro.api.history.repository.HistoryRepository;
import com.skysrd.marketboro.api.member.domain.entity.Member;
import com.skysrd.marketboro.api.member.repository.MemberRepository;
import com.skysrd.marketboro.api.member.service.MemberService;
import com.skysrd.marketboro.common.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final MemberService memberService;

    public Page<HistoryResponse> getHistory(HistorySearchRequest historySearchRequest) {
        Member member = memberService.getMember(historySearchRequest.getMemberId());

        PageRequest page = PageRequest.of(historySearchRequest.getOffset(), historySearchRequest.getLimit(), historySearchRequest.getDirection(), historySearchRequest.getOrderBy());

        return historyRepository.findPageByRewardTypeAndMember(historySearchRequest.getRewardType(), member, page)
                .map(HistoryResponse::toResponse);
    }

    @Transactional
    public void saveHistory(HistorySaveRequest historySaveRequest) {
        History history = History.createBuilder()
                .member(historySaveRequest.getMember())
                .price(historySaveRequest.getPrice())
                .rewardType(historySaveRequest.getRewardType())
                .build();

        historyRepository.save(history);
    }
}
