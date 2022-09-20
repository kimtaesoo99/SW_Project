package com.example.sheetmusiclist.service.sheetmusic;


import com.example.sheetmusiclist.dto.sheetmusic.*;
import com.example.sheetmusiclist.entity.image.Image;
import com.example.sheetmusiclist.entity.member.Member;
import com.example.sheetmusiclist.entity.sheetmusic.SheetMusic;
import com.example.sheetmusiclist.exception.MemberNotEqualsException;
import com.example.sheetmusiclist.exception.SheetMusicNotFoundException;
import com.example.sheetmusiclist.repository.sheetmusic.SheetMusicRepository;
import com.example.sheetmusiclist.service.file.FileService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class SheetMusicService {

    private final SheetMusicRepository sheetMusicRepository;

    private final FileService fileService;
    // 악보 등록
    @Transactional
    public void createSheetMusic(SheetMusicCreateRequestDto req, Member member) {
        List<Image> images = req.getImages().stream().map(i -> new Image(i.getOriginalFilename())).collect(toList());
        SheetMusic sheetMusic = sheetMusicRepository.save(new SheetMusic(member,req.getTitle(),req.getSongwriter(),images));
        uploadImages(sheetMusic.getImages(), req.getImages());

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

    //악보 검색 하기
    @Transactional(readOnly = true)
    public List<SheetMusicSearchResponseDto> searchTitleSheetMusic(SheetMusicSearchRequestDto req){
        List<SheetMusic> sheetMusics = sheetMusicRepository.findAllByTitleContaining(req.getSearchKeyWord());
        List<SheetMusicSearchResponseDto> result = new ArrayList<>();
        sheetMusics.forEach(s->result.add(SheetMusicSearchResponseDto.toDto(s)));
        return result;
    }
    //악보 검색 하기
    @Transactional(readOnly = true)
    public List<SheetMusicSearchResponseDto> searchWriterSheetMusic(SheetMusicSearchRequestDto req){
        List<SheetMusic> sheetMusics = sheetMusicRepository.findAllByWriterContaining(req.getSearchKeyWord());
        List<SheetMusicSearchResponseDto> result = new ArrayList<>();
        sheetMusics.forEach(s->result.add(SheetMusicSearchResponseDto.toDto(s)));
        return result;
    }


    // 악보 수정
    @Transactional
    public void editSheetMusic(Long id, Member member, SheetMusicEditRequestDto req) {

        SheetMusic sheetMusic = sheetMusicRepository.findById(id).orElseThrow(SheetMusicNotFoundException::new);
        if (!sheetMusic.getMember().equals(member)) {
            throw new MemberNotEqualsException();
        }

        SheetMusic.ImageUpdatedResult result = sheetMusic.update(req);
        uploadImages(result.getAddedImages(), result.getAddedImageFiles());
        deleteImages(result.getDeletedImages());

    }

    // 악보 삭제
    @Transactional
    public void deleteSheetMusic(Long id, Member member) {

        SheetMusic sheetMusic = sheetMusicRepository.findById(id).orElseThrow(SheetMusicNotFoundException::new);

        if (!sheetMusic.getMember().getNickname().equals(member.getNickname())) {
            throw new MemberNotEqualsException();
        }

        sheetMusicRepository.deleteById(id);

    }
    private void uploadImages(List<Image> images, List<MultipartFile> fileImages) {
        IntStream.range(0, images.size()).forEach(i -> fileService.upload(fileImages.get(i), images.get(i).getUniqueName()));
    }

    private void deleteImages(List<Image> images) {
        images.stream().forEach(i -> fileService.delete(i.getUniqueName()));
    }

}





