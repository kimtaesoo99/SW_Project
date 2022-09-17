package com.example.sheetmusiclist.service.sheetmusic;


import com.example.sheetmusiclist.dto.sheetmusic.SheetMusicCreateRequestDto;
import com.example.sheetmusiclist.dto.sheetmusic.SheetMusicEditRequestDto;
import com.example.sheetmusiclist.dto.sheetmusic.SheetMusicFindAllResponseDto;
import com.example.sheetmusiclist.dto.sheetmusic.SheetMusicFindResponseDto;
import com.example.sheetmusiclist.entity.member.Member;
import com.example.sheetmusiclist.entity.sheetmusic.SheetMusic;
import com.example.sheetmusiclist.exception.MemberNotEqualsException;
import com.example.sheetmusiclist.exception.SheetMusicNotFoundException;
import com.example.sheetmusiclist.repository.sheetmusic.SheetMusicRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service

public class SheetMusicService {

    private final SheetMusicRepository sheetMusicRepository;

    // 악보 등록
    @Transactional
    public void createSheetMusic(SheetMusicCreateRequestDto sheetMusicCreateRequestDto,
                                 Member member) {

        SheetMusic sheetMusic = new SheetMusic(member, sheetMusicCreateRequestDto.getTitle(),
                sheetMusicCreateRequestDto.getSongwriter());

        sheetMusicRepository.save(sheetMusic);
    }

    // 악보 전체 조회

    @Transactional(readOnly = true)
    public List<SheetMusicFindAllResponseDto> findAllSheetMusic() {

        List<SheetMusic> sheetMusics = sheetMusicRepository.findAll();

        List<SheetMusicFindAllResponseDto> result = new ArrayList<>();

        for (SheetMusic sheetMusic : sheetMusics) {
            result.add(SheetMusicFindAllResponseDto.toDto(sheetMusic));
        }
        return result;
    }

    // 악보 단건 조회
    @Transactional(readOnly = true)
    public SheetMusicFindResponseDto findSheetMusic(Long id) {

        SheetMusic sheetMusic = sheetMusicRepository.findById(id).orElseThrow(SheetMusicNotFoundException::new);

        return SheetMusicFindResponseDto.toDto(sheetMusic);
    }

    // 악보 수정
    @Transactional
    public void editSheetMusic(Long id, Member member, SheetMusicEditRequestDto req) {

        SheetMusic sheetMusic = sheetMusicRepository.findById(id).orElseThrow(SheetMusicNotFoundException::new);

        if (!sheetMusic.getMember().getName().equals(member.getName())) {
            throw new MemberNotEqualsException();
        }

        sheetMusic.editSheetMusic(req.getTitle(), req.getSongwriter());
    }

    // 악보 삭제
    @Transactional
    public void deleteSheetMusic(Long id, Member member) {

        SheetMusic sheetMusic = sheetMusicRepository.findById(id).orElseThrow(SheetMusicNotFoundException::new);

        if (!sheetMusic.getMember().getName().equals(member.getName())) {
            throw new MemberNotEqualsException();
        }
        sheetMusicRepository.deleteById(id);

    }

}
