package com.example.sheetmusiclist.controller;

import com.example.sheetmusiclist.dto.review.ReviewCreateRequestDto;
import com.example.sheetmusiclist.dto.review.ReviewEditRequestDto;
import com.example.sheetmusiclist.controller.review.ReviewController;
import com.example.sheetmusiclist.dto.review.ReviewFindRequestDto;
import com.example.sheetmusiclist.entity.member.Member;
import com.example.sheetmusiclist.repository.member.MemberRepository;
import com.example.sheetmusiclist.service.review.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static com.example.sheetmusiclist.factory.MemberFactory.createMember;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ReviewControllerUnitTest {
    @InjectMocks
    ReviewController reviewController;
    @Mock
    ReviewService reviewService;
    @Mock
    MemberRepository memberRepository;

    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void beforeEach(){
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
    }

    @Test
    @DisplayName("createReview")
    public void createReviewTest()throws Exception{
        //given
        ReviewCreateRequestDto req = new ReviewCreateRequestDto(1l,"a",4);
        Member member = createMember();
        Authentication authentication = new UsernamePasswordAuthenticationToken(member.getId(), "", Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        given(memberRepository.findByUsername(authentication.getName())).willReturn(Optional.of(member));

        //when
        mockMvc.perform(
                post("/api/reviews")
                        .content(objectMapper.writeValueAsString(req))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());


        //then
        verify(reviewService).createReview(member,req);
    }

    @Test
    @DisplayName("getReview")
    public void getReviewTest()throws Exception{
        //given
        ReviewFindRequestDto req = new ReviewFindRequestDto(1l);

        //when
        mockMvc.perform(
                get("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req))
        ).andExpect(status().isOk());

        //then
        verify(reviewService).findReviews(req);
    }

    @Test
    @DisplayName("editReview")
    public void editReviewTest()throws Exception{
        //given
        Long id =1l;
        ReviewEditRequestDto req = new ReviewEditRequestDto(1l,"a",4);
        Member member = createMember();
        Authentication authentication = new UsernamePasswordAuthenticationToken(member.getId(), "", Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        given(memberRepository.findByUsername(authentication.getName())).willReturn(Optional.of(member));

        //when
        mockMvc.perform(
                put("/api/reviews/{id}",id)
                        .content(objectMapper.writeValueAsString(req))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());


        //then
        verify(reviewService).editReview(member,id,req);
    }

    @Test
    @DisplayName("deleteReview")
    public void deleteReviewTest()throws Exception{
        //given
        Long reid =1l;
        Member member = createMember();
        Authentication authentication = new UsernamePasswordAuthenticationToken(member.getId(), "", Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        given(memberRepository.findByUsername(authentication.getName())).willReturn(Optional.of(member));

        //when
        mockMvc.perform(
                delete("/api/reviews/{id}",reid)
        ).andExpect(status().isOk());


        //then
        verify(reviewService).deleteReview(reid,member);
    }


}
