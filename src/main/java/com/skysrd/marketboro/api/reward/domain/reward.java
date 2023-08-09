package com.skysrd.marketboro.api.reward.domain;

import com.skysrd.marketboro.api.member.domain.entity.Member;
import com.skysrd.marketboro.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "rewards")
@NoArgsConstructor
public class Reward extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reward_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "balance")
    private Integer balance;

    @Column(name = "initial_price")
    private Integer initialPrice;

    @Builder(builderClassName = "createBuilder", builderMethodName = "createBuilder")
    public Reward(Long id, Member member, Integer initialPrice) {
        this.id = id;
        this.member = member;
        this.balance = initialPrice;
        this.initialPrice = initialPrice;
    }

    public void subtractBalance(Integer balance) {
        this.balance -= balance;
    }

    public void addBalance(Integer balance) {
        this.balance += balance;
    }

    public void setInitialPrice(Integer initialPrice) {
        this.initialPrice = initialPrice;
    }
}
