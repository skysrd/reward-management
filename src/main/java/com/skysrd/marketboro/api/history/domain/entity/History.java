package com.skysrd.marketboro.api.history.domain.entity;

import com.skysrd.marketboro.api.history.domain.enums.RewardType;
import com.skysrd.marketboro.api.member.domain.entity.Member;
import com.skysrd.marketboro.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "history")
@NoArgsConstructor
public class History extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "price")
    private Integer price;

    @Enumerated(EnumType.STRING)
    @Column(name = "reward_type")
    private RewardType rewardType;

    @Builder(builderClassName = "createBuilder", builderMethodName = "createBuilder")
    public History(Long id, Member member, Integer price, RewardType rewardType) {
        this.id = id;
        this.member = member;
        this.price = price;
        this.rewardType = rewardType;
    }
}
