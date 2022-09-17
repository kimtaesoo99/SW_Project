package com.example.sheetmusiclist.controller.review;


import com.example.sheetmusiclist.dto.review.CreateReviewRequestDto;
import com.example.sheetmusiclist.dto.review.EditReviewRequestDto;
import com.example.sheetmusiclist.entity.member.Member;
import com.example.sheetmusiclist.repository.member.MemberRepository;
import com.example.sheetmusiclist.response.Response;
import com.example.sheetmusiclist.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;
    private final MemberRepository memberRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/reviews")
    public Response createReview(@Valid @RequestBody CreateReviewRequestDto req){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberRepository.findByUsername(authentication.getName()).get();
        reviewService.createReview(member,req);
        return Response.success("리뷰 작성 완료");
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/reviews/{id}")
    public Response editReview(@PathVariable("id") Long id, @Valid @RequestBody EditReviewRequestDto req){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberRepository.findByUsername(authentication.getName()).get();
        reviewService.editReview(member,id,req);
        return Response.success("리뷰 수정 완료");
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/reviews/{id}")
    public Response getReview(@PathVariable("id") Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberRepository.findByUsername(authentication.getName()).get();
        return Response.success(reviewService.findReviews(id));
    }
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/reviews/{id}")
    public Response deleteReview(@PathVariable("id") Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberRepository.findByUsername(authentication.getName()).get();
        reviewService.deleteReview(member,id);
        return Response.success("삭제 완료");
    }




}
