package com.example.sheetmusiclist.entity.sheetmusic;


import com.example.sheetmusiclist.entity.common.EntityDate;
import com.example.sheetmusiclist.entity.member.Member;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class SheetMusic extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name = "member_id")
    private Member member; // 등록한 사람

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String songwriter;

    // + 이미지 추가해야됨(pdf)

    @Builder
    public SheetMusic(Member member, String title, String songwriter) {
        this.member = member;
        this.title = title;
        this.songwriter = songwriter;
    }

    public SheetMusic editSheetMusic(String title, String songwriter) {
        this.title = title;
        this.songwriter = songwriter;

        return this;
    }

}
