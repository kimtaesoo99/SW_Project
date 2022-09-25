package com.example.sheetmusiclist.service.sheetmusic;


import com.example.sheetmusiclist.dto.sheetmusic.*;
import com.example.sheetmusiclist.entity.pdf.Pdf;
import com.example.sheetmusiclist.entity.member.Member;
import com.example.sheetmusiclist.entity.sheetmusic.SheetMusic;
import com.example.sheetmusiclist.exception.MemberNotEqualsException;
import com.example.sheetmusiclist.exception.SheetMusicNotFoundException;
import com.example.sheetmusiclist.repository.sheetmusic.SheetMusicRepository;
import com.example.sheetmusiclist.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        List<Pdf> pdfs = req.getPdfs().stream().map(i -> new Pdf(i.getOriginalFilename())).collect(toList());
        SheetMusic sheetMusic = sheetMusicRepository.save(new SheetMusic(member, req.getTitle(), req.getWriter(), pdfs));
        uploadPdfs(sheetMusic.getPdfs(), req.getPdfs());

    }

    // 악보 전체 조회함  여기페이징 처리해야함
    @Transactional(readOnly = true)
    public List<SheetMusicFindAllResponseDto> findAllSheetMusic(Pageable pageable) {

        Page<SheetMusic> sheetMusics = sheetMusicRepository.findAll(pageable);

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

    //악보 제목으로 검색 하기
    @Transactional(readOnly = true)
    public List<SheetMusicSearchResponseDto> searchTitleSheetMusic(Pageable pageable, String title) {
        Page<SheetMusic> sheetMusics = sheetMusicRepository.findAllByTitleContaining(title,pageable);
        List<SheetMusicSearchResponseDto> result = new ArrayList<>();
        sheetMusics.forEach(s -> result.add(SheetMusicSearchResponseDto.toDto(s)));
        return result;
    }

    //악보 작곡가로 검색 하기
    @Transactional(readOnly = true)
    public List<SheetMusicSearchResponseDto> searchWriterSheetMusic(Pageable pageable,String writer) {
        Page<SheetMusic> sheetMusics = sheetMusicRepository.findAllByWriterContaining(writer,pageable);
        List<SheetMusicSearchResponseDto> result = new ArrayList<>();
        sheetMusics.forEach(s -> result.add(SheetMusicSearchResponseDto.toDto(s)));
        return result;
    }


    // 악보 수정
    @Transactional
    public void editSheetMusic(Long id, Member member, SheetMusicEditRequestDto req) {

        SheetMusic sheetMusic = sheetMusicRepository.findById(id).orElseThrow(SheetMusicNotFoundException::new);
        if (!sheetMusic.getMember().equals(member)) {
            // 로그인 유저(member)랑 이 악보 등록한 사람sheetMusic.getMember()이랑 같은지 다른지 비교
            throw new MemberNotEqualsException(); 
        }

        SheetMusic.PdfUpdatedResult result = sheetMusic.update(req);
        uploadPdfs(result.getAddedPdfs(), result.getAddedPdfFiles());
        deletePdfs(result.getDeletedPdfs());

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

    //requestDto로 들어온 pdf는 multipartfiles인데 이를 stream으로 Pdf엔티디의 데이터 타입으로
    //바꾸고 Pdf(i.getOriginalFilename()) 메솓를 타고 Pdf 엔티디 정보를 채우고 이를 fileService.upload 메소드에
    // 웹페이지에서 받아온 pdf의 번호에 맞는 pdf와, 그 번호에 맞는 stream으로 변환해서 그 해당 uniquefilename을 생성한
    // 그 값을 넘겨준다.
    private void uploadPdfs(List<Pdf> pdfs, List<MultipartFile> filePdfs) {
        IntStream.range(0, pdfs.size()).forEach(i -> fileService.upload(filePdfs.get(i), pdfs.get(i).getUniqueName()));
    }

    private void deletePdfs(List<Pdf> pdfs) {
        pdfs.stream().forEach(i -> fileService.delete(i.getUniqueName()));
    }

}





