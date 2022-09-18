package com.example.sheetmusiclist.factory;

import com.example.sheetmusiclist.entity.member.Authority;
import com.example.sheetmusiclist.entity.member.Member;

public class MemberFactory {
    public static Member createMember() {
        Member member = Member.builder()
                .username("user")
                .password("user123!")
                .email("user@test.com")
                .authority(Authority.ROLE_USER)
                .name("유저")
                .nickname("닉네임")
                .phone("010-1111-1111")
                .address("경기도~")
                .build();

        return member;
    }

}
