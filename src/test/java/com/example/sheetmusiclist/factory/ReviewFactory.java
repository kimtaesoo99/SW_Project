package com.example.sheetmusiclist.factory;

import com.example.sheetmusiclist.entity.member.Member;
import com.example.sheetmusiclist.entity.review.Review;
import com.example.sheetmusiclist.entity.sheetmusic.SheetMusic;

public class ReviewFactory {

    public static Review createReview(Member member, SheetMusic sheetMusic){
        Review review = new Review(member,sheetMusic,"a",1);
        return review;
    }
}
