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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.sheetmusiclist.factory.MemberFactory.createMember;
import static com.example.sheetmusiclist.factory.SheetMusicFactory.createSheetMusic;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
    public void createSheetMusicTest(){
        //given
        SheetMusicCreateRequestDto req = new SheetMusicCreateRequestDto("a","b");
        Member member = createMember();

        //when
        sheetmusicService.createSheetMusic(req,member);

        //then
        verify(sheetMusicRepository).save(any());
    }

    @Test
    @DisplayName("findAllSheetMusic")
    public void findAllSheetMusicTest(){
        //given
        Member member =createMember();
        SheetMusic sheetMusic = createSheetMusic(member);
        List<SheetMusic> sheetMusics = new ArrayList<>();
        sheetMusics.add(sheetMusic);
        given(sheetMusicRepository.findAll()).willReturn(sheetMusics);


        //when
        List<SheetMusicFindAllResponseDto> result =sheetmusicService.findAllSheetMusic();

        //then
        assertThat(result.size()).isEqualTo(sheetMusics.size());
    }

    @Test
    @DisplayName("findSheetMusic")
    public void findSheetMusicTest(){
        //given
        Long id =1l;
        Member member =createMember();
        SheetMusic sheetMusic = createSheetMusic(member);
        given(sheetMusicRepository.findById(id)).willReturn(Optional.of(sheetMusic));


        //when
        SheetMusicFindResponseDto result = sheetmusicService.findSheetMusic(id);

        //then
        assertThat(sheetMusic.getTitle()).isEqualTo(result.getTitle());
    }

    @Test
    @DisplayName("editSheetMusic")
    public void editSheetMusicTest(){
        //given
        Long id =1l;
        Member member =createMember();
        SheetMusic sheetMusic = createSheetMusic(member);
        SheetMusicEditRequestDto req = new SheetMusicEditRequestDto("a","b");
        given(sheetMusicRepository.findById(id)).willReturn(Optional.of(sheetMusic));

        //when
        sheetmusicService.editSheetMusic(id,member,req);

        //then
        assertThat(sheetMusic.getTitle()).isEqualTo(req.getTitle());
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
