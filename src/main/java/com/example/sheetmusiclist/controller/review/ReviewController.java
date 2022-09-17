package com.example.sheetmusiclist.controller.review;


import com.example.sheetmusiclist.config.auth.review.CreateReviewRequestDto;
import com.example.sheetmusiclist.config.auth.review.EditReviewRequestDto;
import com.example.sheetmusiclist.entity.member.Member;
import com.example.sheetmusiclist.entity.sheetmusic.SheetMusic;
import com.example.sheetmusiclist.exception.MemberNotFoundException;
import com.example.sheetmusiclist.exception.SheetMusicNotFoundException;
import com.example.sheetmusiclist.repository.member.MemberRepository;
import com.example.sheetmusiclist.repository.sheetmusic.SheetMusicRepository;
import com.example.sheetmusiclist.response.Response;
import com.example.sheetmusiclist.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;
    private final MemberRepository memberRepository;

    // 리뷰 작성
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}/reviews")
    public Response createReview(@PathVariable("id") Long id, @Valid @RequestBody CreateReviewRequestDto req){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberRepository.findByUsername(authentication.getName()).orElseThrow(MemberNotFoundException::new);

        reviewService.createReview(id, member,req);
        return Response.success("리뷰 작성 완료");
    }

    // 리뷰 전체 조회(by 악보)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/reviews")
    public Response getReview(@PathVariable("id") Long id){

        return Response.success(reviewService.findReviews(id));
    }
    // 리뷰 수정
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{sheetmusicid}/reviews/{reviewid}")
    public Response editReview(@PathVariable("sheetmusicid") Long sheetmusicid,
                               @PathVariable("reviewid") Long reviewid,
                               @Valid @RequestBody EditReviewRequestDto req){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberRepository.findByUsername(authentication.getName()).orElseThrow(MemberNotFoundException::new);

        reviewService.editReview(member, sheetmusicid, reviewid, req);
        return Response.success("리뷰 수정 완료");
    }

    //리뷰 삭제
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{sheetmusicid}/reviews/{reviewid}")
    public Response deleteReview(@PathVariable("sheetmusicid") Long sheetmusicid,
                                 @PathVariable("reviewjd") Long reviewid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberRepository.findByUsername(authentication.getName()).orElseThrow(MemberNotFoundException::new);
        reviewService.deleteReview(sheetmusicid, reviewid, member);
        return Response.success("삭제 완료");
    }
}
