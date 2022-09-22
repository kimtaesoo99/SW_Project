package com.example.sheetmusiclist.controller;


import com.example.sheetmusiclist.controller.sheetmusic.SheetMusicController;
import com.example.sheetmusiclist.dto.sheetmusic.SheetMusicCreateRequestDto;
import com.example.sheetmusiclist.dto.sheetmusic.SheetMusicEditRequestDto;
import com.example.sheetmusiclist.entity.member.Member;
import com.example.sheetmusiclist.repository.member.MemberRepository;
import com.example.sheetmusiclist.service.sheetmusic.SheetMusicService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.sheetmusiclist.factory.MemberFactory.createMember;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class SheetMusicControllerUnitTest {
    @InjectMocks
    SheetMusicController sheetMusicController;
    @Mock
    SheetMusicService sheetMusicService;
    @Mock
    MemberRepository memberRepository;

    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void beforeEach(){
        mockMvc = MockMvcBuilders.standaloneSetup(sheetMusicController).build();
    }

    //여기 수정해야함.
    @Test
    @DisplayName("createSheetMusic")
    public void createSheetMusicTest()throws Exception{
        //given
        ArgumentCaptor<SheetMusicCreateRequestDto> productCreateRequestDtoArgumentCaptor = ArgumentCaptor.forClass(SheetMusicCreateRequestDto.class);
        List<MultipartFile> imageFiles = List.of(
                new MockMultipartFile("test1", "test1.PNG", MediaType.IMAGE_PNG_VALUE, "test1".getBytes()),
                new MockMultipartFile("test2", "test2.PNG", MediaType.IMAGE_PNG_VALUE, "test2".getBytes())
        );


        SheetMusicCreateRequestDto req = new SheetMusicCreateRequestDto("a","b",imageFiles);
        Member member = createMember();
        Authentication authentication = new UsernamePasswordAuthenticationToken(member.getId(), "", Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        given(memberRepository.findByUsername(authentication.getName())).willReturn(Optional.of(member));

        //when
        mockMvc.perform(
                post("/api/sheetmusics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req))
        ).andExpect(status().isCreated());

        //then
        verify(sheetMusicService).createSheetMusic(req,member);
    }

    @Test
    @DisplayName("findAllSheetMusic")
    public void findAllSheetMusicTest()throws Exception{
        //given

        //when
        mockMvc.perform(
                get("/api/sheetmusics")
        ).andExpect(status().isOk());

        //then
        verify(sheetMusicService).findAllSheetMusic();
    }

    @Test
    @DisplayName("findSheetMusic")
    public void findSheetMusicTest()throws Exception{
        //given
        Long id =1l;

        //when
        mockMvc.perform(
                get("/api/sheetmusics/{id}",id)
        ).andExpect(status().isOk());

        //then
        verify(sheetMusicService).findSheetMusic(id);
    }


    @Test
    @DisplayName("editSheetMusic")
    public void editSheetMusicTest()throws Exception{
        //given

        Long id =1l;
        SheetMusicEditRequestDto req = new SheetMusicEditRequestDto("a","b");
        Member member = createMember();
        Authentication authentication = new UsernamePasswordAuthenticationToken(member.getId(), "", Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        given(memberRepository.findByUsername(authentication.getName())).willReturn(Optional.of(member));


        //when
        mockMvc.perform(
                put("/api/sheetmusics/{id}",id)
                        .content(objectMapper.writeValueAsString(req))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        //then
        verify(sheetMusicService).editSheetMusic(id,member,req);
    }

    @Test
    @DisplayName("deleteSheetMusic")
    public void deleteSheetMusicTest()throws Exception{
        //given
        Long id =1l;
        Member member = createMember();
        Authentication authentication = new UsernamePasswordAuthenticationToken(member.getId(), "", Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        given(memberRepository.findByUsername(authentication.getName())).willReturn(Optional.of(member));


        //when
        mockMvc.perform(
                delete("/api/sheetmusics/{id}",id)
        ).andExpect(status().isOk());

        //then
        verify(sheetMusicService).deleteSheetMusic(id,member);
    }
}
