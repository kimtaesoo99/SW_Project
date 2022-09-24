package com.example.sheetmusiclist.service.review;


import com.example.sheetmusiclist.dto.review.*;
import com.example.sheetmusiclist.entity.member.Member;
import com.example.sheetmusiclist.entity.review.Review;
import com.example.sheetmusiclist.entity.sheetmusic.SheetMusic;
import com.example.sheetmusiclist.exception.*;
import com.example.sheetmusiclist.repository.review.ReviewRepository;
import com.example.sheetmusiclist.repository.sheetmusic.SheetMusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    private final SheetMusicRepository sheetMusicRepository;
    //리뷰를 작성, 리뷰를 수정, 리뷰 삭제, 리뷰보기(한 정보에 대해)

    //리뷰 작성
    @Transactional
    public void createReview(Member member, ReviewCreateRequestDto req){

        SheetMusic sheetmusic = sheetMusicRepository.findById(req.getSheetMusicId()).orElseThrow(SheetMusicNotFoundException::new);
        Review review = new Review(member, sheetmusic, req.getComment(), req.getRate());
        reviewRepository.save(review);
    }

    //리뷰 수정
    @Transactional
    public void editReview(Member member, Long reviewid, ReviewEditRequestDto req) {
       Review review = reviewRepository.findById(reviewid).orElseThrow(ReviewNotFoundException::new);

        if (!member.getNickname().equals(review.getMember().getNickname())) {
            throw new MemberNotEqualsException();
        }
        review.editReview(req.getComment(), req.getRate());
    }

    //리뷰 전체 조회(by 악보)
    @Transactional(readOnly = true)
    public List<ReviewfindResponseDto> findReviews(Pageable pageable,Long id){
        SheetMusic sheetmusic = sheetMusicRepository.findById(id).orElseThrow(SheetMusicNotFoundException::new);
        Page<Review> reviews = reviewRepository.findAllBySheetmusic(sheetmusic,pageable);
        List<ReviewfindResponseDto> result = new ArrayList<>();
        for (Review review : reviews) {
            result.add(ReviewfindResponseDto.toDto(review));
        }
        return result;
    }
    //리뷰 삭제
    @Transactional
    public void deleteReview(Long id, Member member){
       Review review = reviewRepository.findById(id).orElseThrow(ReviewNotFoundException::new);

        if (!member.getNickname().equals(review.getMember().getNickname())) {
            throw new MemberNotEqualsException();
        }
        reviewRepository.delete(review);

    }

}
