package com.skysrd.marketboro.api.member.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@Table(name = "members")
@Entity
@NoArgsConstructor
public class Member {

    //사용자 Id
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "reward_summary")
    private Integer summary;

    //사용자 생성 빌더
    @Builder(builderClassName = "createBuilder", builderMethodName = "createBuilder")
    public Member(Long id) {
        this.id = id;
        this.summary = 0;
    }

    public void setSummary(Integer summary) {
        this.summary = summary;
    }
}
