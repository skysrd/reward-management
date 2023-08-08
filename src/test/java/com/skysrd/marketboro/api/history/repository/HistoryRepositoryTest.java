package com.skysrd.marketboro.api.history.repository;

import com.skysrd.marketboro.api.history.domain.entity.History;
import com.skysrd.marketboro.api.history.domain.enums.RewardType;
import com.skysrd.marketboro.api.member.domain.entity.Member;
import com.skysrd.marketboro.api.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class HistoryRepositoryTest {

    Member PREFIX_MEMBER_01 = Member.createBuilder()
            .id(1L)
            .build();

    History PREFIX_HISTORY_01 = History.createBuilder()
            .id(1L)
            .member(PREFIX_MEMBER_01)
            .price(100)
            .rewardType(RewardType.SAVE)
            .build();

    History PREFIX_HISTORY_02 = History.createBuilder()
            .id(2L)
            .member(PREFIX_MEMBER_01)
            .price(50)
            .rewardType(RewardType.SPEND)
            .build();

    History PREFIX_HISTORY_03 = History.createBuilder()
            .id(3L)
            .member(PREFIX_MEMBER_01)
            .price(50)
            .rewardType(RewardType.SAVE)
            .build();

    History PREFIX_HISTORY_04 = History.createBuilder()
            .id(4L)
            .member(PREFIX_MEMBER_01)
            .price(1)
            .rewardType(RewardType.SAVE)
            .build();

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void findPageByRewardTypeAndMember() {
        //given
        memberRepository.save(PREFIX_MEMBER_01);

        List<History> historyList = new ArrayList<>();
        historyList.add(PREFIX_HISTORY_01);
        historyList.add(PREFIX_HISTORY_02);
        historyList.add(PREFIX_HISTORY_03);
        historyList.add(PREFIX_HISTORY_04);

        historyRepository.saveAll(historyList);

        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        Page<History> saveHistoryResponsePage = historyRepository
                .findPageByRewardTypeAndMember(RewardType.SAVE, PREFIX_MEMBER_01, pageRequest);

        Page<History> spendHistoryResponsePage = historyRepository.findPageByRewardTypeAndMember(RewardType.SPEND, PREFIX_MEMBER_01, pageRequest);

        //then
        assertEquals(PREFIX_HISTORY_01.getId(), saveHistoryResponsePage.getContent().get(0).getId());
        assertEquals(PREFIX_HISTORY_01.getPrice(), saveHistoryResponsePage.getContent().get(0).getPrice());
        assertEquals(PREFIX_HISTORY_01.getRewardType(), saveHistoryResponsePage.getContent().get(0).getRewardType());
        assertEquals(PREFIX_HISTORY_03.getId(), saveHistoryResponsePage.getContent().get(1).getId());
        assertEquals(PREFIX_HISTORY_03.getPrice(), saveHistoryResponsePage.getContent().get(1).getPrice());
        assertEquals(PREFIX_HISTORY_03.getRewardType(), saveHistoryResponsePage.getContent().get(1).getRewardType());
        assertEquals(PREFIX_HISTORY_04.getId(), saveHistoryResponsePage.getContent().get(2).getId());
        assertEquals(PREFIX_HISTORY_04.getPrice(), saveHistoryResponsePage.getContent().get(2).getPrice());
        assertEquals(PREFIX_HISTORY_04.getRewardType(), saveHistoryResponsePage.getContent().get(2).getRewardType());
        assertEquals(PREFIX_HISTORY_02.getId(), spendHistoryResponsePage.getContent().get(0).getId());
        assertEquals(PREFIX_HISTORY_02.getPrice(), spendHistoryResponsePage.getContent().get(0).getPrice());
        assertEquals(PREFIX_HISTORY_02.getRewardType(), spendHistoryResponsePage.getContent().get(0).getRewardType());
    }
}