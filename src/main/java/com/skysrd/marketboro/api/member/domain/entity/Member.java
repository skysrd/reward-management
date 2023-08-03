package com.skysrd.marketboro.api.member.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(name = "members")
@Entity
public class Member {

    //사용자 Id
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    //사용자 생성 빌더
    @Builder(builderClassName = "createBuilder", builderMethodName = "createBuilder")
    public Member() {
    }
}
