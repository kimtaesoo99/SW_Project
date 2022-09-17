package com.example.sheetmusiclist.entity.review;

import com.example.sheetmusiclist.entity.common.EntityDate;
import com.example.sheetmusiclist.entity.member.Member;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Review extends EntityDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "memer_id")
    private Member member;


    @Column(nullable = false)
    private String comment;

    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer rate;

    public Review(Member member,String comment, Integer rate){
        this.member =member;
        this.comment = comment;
        this.rate = rate;
    }

    public Review editReview(String comment, Integer rate){
        this.comment = comment;
        this.rate =rate;
        return this;
    }
}
