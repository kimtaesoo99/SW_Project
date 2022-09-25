package com.example.sheetmusiclist.service;


import com.example.sheetmusiclist.dto.sheetmusic.SheetMusicCreateRequestDto;
import com.example.sheetmusiclist.dto.sheetmusic.SheetMusicEditRequestDto;
import com.example.sheetmusiclist.dto.sheetmusic.SheetMusicFindAllResponseDto;
import com.example.sheetmusiclist.dto.sheetmusic.SheetMusicFindResponseDto;
import com.example.sheetmusiclist.entity.member.Member;
import com.example.sheetmusiclist.entity.sheetmusic.SheetMusic;
import com.example.sheetmusiclist.repository.sheetmusic.SheetMusicRepository;
import com.example.sheetmusiclist.service.sheetmusic.SheetMusicService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static com.example.sheetmusiclist.factory.ImageFactory.createImage;
import static com.example.sheetmusiclist.factory.MemberFactory.createMember;
import static com.example.sheetmusiclist.factory.SheetMusicFactory.createSheetMusic;
import static com.example.sheetmusiclist.factory.SheetMusicFactory.createSheetMusicWithImages;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SheetMusicServiceUnitTest {

    @InjectMocks
    SheetMusicService sheetmusicService;

    @Mock
    SheetMusicRepository sheetMusicRepository;

    @Test
    @DisplayName("createSheetMusic")
    public void createSheetMusicTest() {
        // given
        SheetMusicCreateRequestDto req = new SheetMusicCreateRequestDto("제목", "내용", List.of(
                new MockMultipartFile("test1", "test1.PNG", MediaType.IMAGE_PNG_VALUE, "test1".getBytes()),
                new MockMultipartFile("test2", "test2.PNG", MediaType.IMAGE_PNG_VALUE, "test2".getBytes()),
                new MockMultipartFile("test3", "test3.PNG", MediaType.IMAGE_PNG_VALUE, "test3".getBytes())
        ));


        Member member = createMember();

        given(sheetMusicRepository.save(any())).willReturn(createSheetMusicWithImages(
                createMember(), IntStream.range(0, req.getPdfs().size()).mapToObj(i -> createImage()).collect(toList()))
        );

        // when
        sheetmusicService.createSheetMusic(req, member);

        // then
        verify(sheetMusicRepository).save(any());
    }


    @Test
    @DisplayName("findAllSheetMusic")
    public void findAllSheetMusicTest() {
        // given
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");
        List<SheetMusic> sheetMusics = sheetMusicRepository.findAll();
        sheetMusics.add(createSheetMusic(createMember()));
        Page<SheetMusic> res = new PageImpl<>(sheetMusics);
        given(sheetMusicRepository.findAll(pageable)).willReturn(res);
        // when
        List<SheetMusicFindAllResponseDto> result = sheetmusicService.findAllSheetMusic(pageable);

        // then
        assertThat(result.get(0).getTitle()).isEqualTo(createSheetMusic(createMember()).getTitle());
    }


    @Test
    @DisplayName("findSheetMusic")
    public void findSheetMusicTest() {
        // given
        Long id = 1L;
        Member member = createMember();
        SheetMusic sheetMusic = createSheetMusic(member);
        given(sheetMusicRepository.findById(id)).willReturn(Optional.of(sheetMusic));

        // when
        SheetMusicFindResponseDto result = sheetmusicService.findSheetMusic(id);

        // then
        verify(sheetMusicRepository).findById(anyLong());
        assertThat(result.getNickName()).isEqualTo(member.getNickname());
    }

    //제목 검색
    @Test
    @DisplayName("searchTitleSheetMusic")
    public void searchTitleSheetMusicTest() {
        //given

        //when

        //then
    }


    @Test
    @DisplayName("editSheetMusic")
    public void editSheetMusicTest() {
        // given
        Long id = 1L;
        SheetMusicEditRequestDto req = new SheetMusicEditRequestDto("제목2", "내용2", List.of(
                new MockMultipartFile("test1", "test1.PNG", MediaType.IMAGE_PNG_VALUE, "test1".getBytes()),
                new MockMultipartFile("test2", "test2.PNG", MediaType.IMAGE_PNG_VALUE, "test2".getBytes()),
                new MockMultipartFile("test3", "test3.PNG", MediaType.IMAGE_PNG_VALUE, "test3".getBytes())
        ), List.of(1, 2));
        Member member = createMember();
        SheetMusic sheetMusic = createSheetMusic(member);
        given(sheetMusicRepository.findById(id)).willReturn(Optional.of(sheetMusic));

        // when
        sheetmusicService.editSheetMusic(id, member,req);

        // then
        assertThat(sheetMusicRepository.findById(id).get().getWriter()).isEqualTo("내용2");
    }



    @Test
    @DisplayName("deleteMusic")
    public void deleteSheetMusicTest(){
        //given
        Long id =1l;
        Member member =createMember();
        SheetMusic sheetMusic = createSheetMusic(member);
        given(sheetMusicRepository.findById(id)).willReturn(Optional.of(sheetMusic));

        //when
        sheetmusicService.deleteSheetMusic(id,member);

        //then
        verify(sheetMusicRepository).deleteById(id);
    }
}
