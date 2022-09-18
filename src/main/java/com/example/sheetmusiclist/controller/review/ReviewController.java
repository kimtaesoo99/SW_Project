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
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "리뷰 작성", notes = "리뷰를 작성한다.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}/reviews")
    public Response createReview(@PathVariable("id") Long id, @Valid @RequestBody CreateReviewRequestDto req){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberRepository.findByUsername(authentication.getName()).orElseThrow(MemberNotFoundException::new);

        reviewService.createReview(id, member,req);
        return Response.success("리뷰 작성 완료");
    }

    // 리뷰 전체 조회(by 악보)
    @ApiOperation(value = "해당 악보의 리뷰 전체 조회", notes = "해당 악보의 리뷰를 전체 조회한다.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/reviews")
    public Response getReview(@PathVariable("id") Long id){

        return Response.success(reviewService.findReviews(id));
    }
    // 리뷰 수정
    @ApiOperation(value = "리뷰 수정", notes = "리뷰를 수정한다.")
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
    @ApiOperation(value = "리뷰 삭제", notes = "리뷰를 삭제한다.")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{sheetmusicid}/reviews/{reviewid}")
    public Response deleteReview(@PathVariable("sheetmusicid") Long sheetmusicid,
                                 @PathVariable("reviewid") Long reviewid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberRepository.findByUsername(authentication.getName()).orElseThrow(MemberNotFoundException::new);
        reviewService.deleteReview(sheetmusicid, reviewid, member);
        return Response.success("삭제 완료");
    }
}
