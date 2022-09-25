package com.example.sheetmusiclist.controller;


import com.example.sheetmusiclist.controller.sheetmusic.SheetMusicController;
import com.example.sheetmusiclist.dto.sheetmusic.SheetMusicCreateRequestDto;
import com.example.sheetmusiclist.dto.sheetmusic.SheetMusicEditRequestDto;
import com.example.sheetmusiclist.dto.sheetmusic.SheetMusicSearchRequestDto;
import com.example.sheetmusiclist.entity.member.Member;
import com.example.sheetmusiclist.entity.sheetmusic.SheetMusic;
import com.example.sheetmusiclist.repository.member.MemberRepository;
import com.example.sheetmusiclist.repository.sheetmusic.SheetMusicRepository;
import com.example.sheetmusiclist.service.sheetmusic.SheetMusicService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import static org.assertj.core.api.Assertions.assertThat;
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
    @Mock
    SheetMusicRepository sheetMusicRepository;

    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void beforeEach(){
        mockMvc = MockMvcBuilders.standaloneSetup(sheetMusicController).build();
    }

    //악보 등록
    @Test
    @DisplayName("createSheetMusic")
    public void createSheetMusicTest()throws Exception{
        //given
        List<MultipartFile> imageFiles = List.of(
                new MockMultipartFile("test1", "test1.PNG", MediaType.IMAGE_PNG_VALUE, "test1".getBytes()),
                new MockMultipartFile("test2", "test2.PNG", MediaType.IMAGE_PNG_VALUE, "test2".getBytes())
        );
        SheetMusicCreateRequestDto req = new SheetMusicCreateRequestDto("a","b",imageFiles);
        Member member = createMember();
        Authentication authentication = new UsernamePasswordAuthenticationToken(member.getId(), "", Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        given(memberRepository.findByUsername(authentication.getName())).willReturn(Optional.of(member));

        // when
        mockMvc.perform(
                        multipart("/api/sheetmusics")
                                .file("images", imageFiles.get(0).getBytes())
                                .file("images", imageFiles.get(1).getBytes())
                                .param("title", req.getTitle())
                                .param("writer", req.getWriter())
                                .with(requestPostProcessor -> {
                                    requestPostProcessor.setMethod("POST");
                                    return requestPostProcessor;
                                })
                                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());

        //then
        assertThat(req.getPdfs().size()).isEqualTo(2);
    }

    //악보 전체조회
    @Test
    @DisplayName("findAllSheetMusic")
    public void findAllSheetMusicTest() throws Exception {
        // given
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");
        Page<SheetMusic> result = sheetMusicRepository.findAll(pageable);

        // when
        mockMvc.perform(
                get("/api/sheetmusics")
        ).andExpect(status().isOk());

        // then
        verify(sheetMusicService).findAllSheetMusic(pageable);
        assertThat(result).isEqualTo(null);
    }

    //악보 단건조회
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
    //악보 제못 제목으로 검색
    @Test
    @DisplayName("searchTitlesheetmusic")
    public void searchTitleSheetMusicTest() throws Exception{
        //given
        String str = "아이유";
        SheetMusicSearchRequestDto req = new SheetMusicSearchRequestDto("a");
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");
        Page<SheetMusic> result = sheetMusicRepository.findAll(pageable);


        //when
        mockMvc.perform(
                get("/api/sheetmusics/searchtitle")
                        .content(objectMapper.writeValueAsString(req))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        //then
        verify(sheetMusicService).searchTitleSheetMusic(pageable,str);
        assertThat(result).isEqualTo(null);
    }


    //악보 작곡가 제목으로 검색
    @Test
    @DisplayName("searchWritersheetmusic")
    public void searchWriterSheetMusicTest()throws Exception{
        //given

        String str = "복숭아";
        SheetMusicSearchRequestDto req = new SheetMusicSearchRequestDto("a");
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");
        Page<SheetMusic> result = sheetMusicRepository.findAll(pageable);

        //when
        mockMvc.perform(
                get("/api/sheetmusics/searchwriter")
        ).andExpect(status().isOk());

        //then
        verify(sheetMusicService).searchWriterSheetMusic(pageable,str);
        assertThat(result).isEqualTo(null);
    }


    //악보 수정
    @Test
    @DisplayName("editSheetMusic")
    public void editSheetMusicTest()throws Exception{
        // given
        Long id = 1L;

        Member member = createMember();
        Authentication authentication = new UsernamePasswordAuthenticationToken(member.getId(), "", Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        List<MultipartFile> addedImages = List.of(
                new MockMultipartFile("test1", "test1.PNG", MediaType.IMAGE_PNG_VALUE, "test1".getBytes()),
                new MockMultipartFile("test2", "test2.PNG", MediaType.IMAGE_PNG_VALUE, "test2".getBytes())
        );
        List<Integer> deletedImages = List.of(1, 2);

        SheetMusicEditRequestDto req = new SheetMusicEditRequestDto("2", "2",  addedImages, deletedImages);
        given(memberRepository.findByUsername(authentication.getName())).willReturn(Optional.of(member));

        // when
        mockMvc.perform(
                        multipart("/api/sheetmusics/{id}", 1L)
                                .file("addedImages", addedImages.get(0).getBytes())
                                .file("addedImages", addedImages.get(1).getBytes())
                                .param("deletedImages", String.valueOf(deletedImages.get(0)), String.valueOf(deletedImages.get(1)))
                                .param("title", req.getTitle())
                                .param("writer", req.getWriter())
                                .with(requestPostProcessor -> {
                                    requestPostProcessor.setMethod("PUT");
                                    return requestPostProcessor;
                                })
                                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());

        // then
        assertThat(req.getAddedPdfs().size()).isEqualTo(2);
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
