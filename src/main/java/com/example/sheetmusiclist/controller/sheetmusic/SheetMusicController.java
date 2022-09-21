package com.example.sheetmusiclist.controller.sheetmusic;


import com.example.sheetmusiclist.dto.sheetmusic.SheetMusicCreateRequestDto;
import com.example.sheetmusiclist.dto.sheetmusic.SheetMusicEditRequestDto;
import com.example.sheetmusiclist.dto.sheetmusic.SheetMusicSearchRequestDto;
import com.example.sheetmusiclist.entity.member.Member;
import com.example.sheetmusiclist.exception.MemberNotFoundException;
import com.example.sheetmusiclist.repository.member.MemberRepository;
import com.example.sheetmusiclist.response.Response;
import com.example.sheetmusiclist.service.sheetmusic.SheetMusicService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class SheetMusicController {

    private final SheetMusicService sheetMusicService;
    private final MemberRepository memberRepository;

    // 악보 등록
    @ApiOperation(value = "악보 등록", notes = "악보를 등록한다.")
    @PostMapping("/sheetmusics")
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(@Valid @ModelAttribute SheetMusicCreateRequestDto sheetMusicCreateRequestDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberRepository.findByUsername(authentication.getName()).orElseThrow(MemberNotFoundException::new);

        sheetMusicService.createSheetMusic(sheetMusicCreateRequestDto, member);

        return Response.success("악보 등록 완료");
    }

    // 악보 전체 조회
    @ApiOperation(value = "악보 전체 조회", notes = "전체 악보를 조회한다.")
    @GetMapping("/sheetmusics")
    @ResponseStatus(HttpStatus.OK)
    public Response findAll() {

        return Response.success(sheetMusicService.findAllSheetMusic());
    }

    // 악보 단건 조회
    @ApiOperation(value = "악보 단건 조회", notes = "악보를 등록한다.")
    @GetMapping("/sheetmusics/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response find(@PathVariable("id") Long id) {

        return Response.success(sheetMusicService.findSheetMusic(id));
    }

    //악보 검색 하기
    @ApiOperation(value = "악보 제목으로 검색 하기", notes = "악보 제목으로 검색하기.")
    @GetMapping("/sheetmusics/searchtitle")
    @ResponseStatus(HttpStatus.OK)
    public Response searchTitle(@Valid @RequestBody SheetMusicSearchRequestDto req){
        return Response.success(sheetMusicService.searchTitleSheetMusic(req));
    }

    //악보 검색 하기
    @ApiOperation(value = "악보 작곡가로 검색 하기", notes = "악보 작곡가로 검색하기.")
    @GetMapping("/sheetmusics/searchwriter")
    @ResponseStatus(HttpStatus.OK)
    public Response searchWriter(@Valid @RequestBody SheetMusicSearchRequestDto req){
        return Response.success(sheetMusicService.searchWriterSheetMusic(req));
    }



    // 악보 수정
    @ApiOperation(value = "악보 수정", notes = "악보를 수정한다.")
    @PutMapping("/sheetmusics/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response edit(@PathVariable("id") Long id, @Valid @ModelAttribute SheetMusicEditRequestDto sheetMusicEditRequestDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberRepository.findByUsername(authentication.getName()).orElseThrow(MemberNotFoundException::new);

        sheetMusicService.editSheetMusic(id, member, sheetMusicEditRequestDto);

        return Response.success("악보 수정 완료");
    }

    // 악보 삭제
    @ApiOperation(value = "악보 삭제", notes = "악보를 삭제한다.")
    @DeleteMapping("/sheetmusics/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response delete(@PathVariable("id") Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberRepository.findByUsername(authentication.getName()).orElseThrow(MemberNotFoundException::new);

        sheetMusicService.deleteSheetMusic(id, member);

        return Response.success("악보 삭제 완료");
    }
}

