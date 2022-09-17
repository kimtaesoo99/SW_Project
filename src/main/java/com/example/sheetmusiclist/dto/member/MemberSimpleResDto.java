package com.example.sheetmusiclist.dto.member;

import com.example.sheetmusiclist.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberSimpleResDto {
    private Long id;
    private String nickname;
    private String phone;
    private String email;
    // 추후에 평점 달기

    public MemberSimpleResDto toDto(Member member) {
        return new MemberSimpleResDto(member.getId(), member.getNickname(), member.getPhone(), member.getEmail());
    }
}
