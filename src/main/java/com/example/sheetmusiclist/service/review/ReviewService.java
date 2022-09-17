package com.example.sheetmusiclist.service.review;


import com.example.sheetmusiclist.SheetmusicListApplication;
import com.example.sheetmusiclist.dto.review.CreateReviewRequestDto;
import com.example.sheetmusiclist.dto.review.EditReviewRequestDto;
import com.example.sheetmusiclist.dto.review.findReviewResponseDto;
import com.example.sheetmusiclist.entity.member.Member;
import com.example.sheetmusiclist.entity.review.Review;
import com.example.sheetmusiclist.exception.ReviewNotFoundException;
import com.example.sheetmusiclist.exception.UserNotEqualsException;
import com.example.sheetmusiclist.repository.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

//    private final SheetMusicRepository sheetMusicRepository;
    //리뷰를 작성, 리뷰를 수정, 리뷰 삭제, 리뷰보기(한 정보에 대해)

    //리뷰 작성
    @Transactional
    public void createReview(Member member,CreateReviewRequestDto req){
        Review review = new Review(member,req.getComment(),req.getRate());
        reviewRepository.save(review);
    }

    //리뷰 수정
    @Transactional
    public void editReview(Member member,Long id,EditReviewRequestDto req){
        Review review = reviewRepository.findById(id).orElseThrow(ReviewNotFoundException::new);
        if (!member.equals(review.getMember()))throw new UserNotEqualsException();
        review.editReview(req.getComment(),req.getRate());
    }
    //리뷰 보기 수정해야함
    @Transactional(readOnly = true)
    public List<findReviewResponseDto> findReviews(Long id){
//        Sheetmusic sheetmusic = sheetMusicRepository.findById(id).get();
//        List<Review> reviews = reviewRepository.findAllBySheetMusic(sheetmusic);
        List<findReviewResponseDto> result = new ArrayList<>();
//        reviews.forEach(s->result.add(findReviewResponseDto.toDto(s)));
        return result;
    }
    //리뷰 삭제
    @Transactional
    public void deleteReview(Member member, Long id){
        Review review = reviewRepository.findById(id).get();
        if (!member.equals(review.getMember()))throw new UserNotEqualsException();
        reviewRepository.delete(review);
    }

}
