package com.example.sheetmusiclist.service;

import com.example.sheetmusiclist.dto.review.ReviewCreateRequestDto;
import com.example.sheetmusiclist.dto.review.ReviewEditRequestDto;
import com.example.sheetmusiclist.dto.review.ReviewFindRequestDto;
import com.example.sheetmusiclist.dto.review.ReviewfindResponseDto;
import com.example.sheetmusiclist.entity.member.Member;
import com.example.sheetmusiclist.entity.review.Review;
import com.example.sheetmusiclist.entity.sheetmusic.SheetMusic;
import com.example.sheetmusiclist.repository.review.ReviewRepository;
import com.example.sheetmusiclist.repository.sheetmusic.SheetMusicRepository;
import com.example.sheetmusiclist.service.review.ReviewService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.sheetmusiclist.factory.MemberFactory.createMember;
import static com.example.sheetmusiclist.factory.ReviewFactory.createReview;
import static com.example.sheetmusiclist.factory.SheetMusicFactory.createSheetMusic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceUnitTest {
    @InjectMocks
    ReviewService reviewService;
    @Mock
    ReviewRepository reviewRepository;
    @Mock
    SheetMusicRepository sheetMusicRepository;

    @Test
    @DisplayName("createReview")
    public void createReviewTest(){
        //given
        ReviewCreateRequestDto req = new ReviewCreateRequestDto(1l,"a",2);
        Member member =createMember();
        SheetMusic sheetMusic = createSheetMusic(member);
        Review review = createReview(member,sheetMusic);
        given(sheetMusicRepository.findById(req.getSheetMusicId())).willReturn(Optional.of(sheetMusic));

        //when
        reviewService.createReview(member,req);

        //then
        verify(reviewRepository).save(any());
    }
    @Test
    @DisplayName("editReview")
    public void editReviewTest(){
        //given
        Long id =1l;
        ReviewEditRequestDto req = new ReviewEditRequestDto(1l,"a",2);
        Member member =createMember();
        SheetMusic sheetMusic = createSheetMusic(member);
        Review review = createReview(member,sheetMusic);
        given(reviewRepository.findById(id)).willReturn(Optional.of(review));

        //when
        reviewService.editReview(member,id,req);

        //then
        assertThat(review.getComment()).isEqualTo(req.getComment());
    }

    @Test
    @DisplayName("findReview")
    public void findReviewTest(){
        //given
        ReviewFindRequestDto req = new ReviewFindRequestDto(1l);
        Member member =createMember();
        SheetMusic sheetMusic = createSheetMusic(member);
        Review review = createReview(member,sheetMusic);
        List<Review> reviews = new ArrayList<>();
        reviews.add(review);
        given(sheetMusicRepository.findById(req.getSheetMusicId())).willReturn(Optional.of(sheetMusic));
//        given(reviewRepository.findAllBySheetmusic(sheetMusic)).willReturn(reviews);

        //when
//        List<ReviewfindResponseDto> result = reviewService.findReviews(req);

        //then
//        assertThat(result.size()).isEqualTo(reviews.size());
    }

    @Test
    @DisplayName("deleteReview")
    public void deleteReviewTest(){
        //given
        Long id =1l;
        Member member =createMember();
        SheetMusic sheetMusic = createSheetMusic(member);
        Review review = createReview(member,sheetMusic);
        given(reviewRepository.findById(id)).willReturn(Optional.of(review));

        //when
        reviewService.deleteReview(id,member);

        //then
        verify(reviewRepository).delete(review);
    }

}
