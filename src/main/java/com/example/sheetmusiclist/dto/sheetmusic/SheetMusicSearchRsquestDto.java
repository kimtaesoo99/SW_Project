package com.example.sheetmusiclist.dto.sheetmusic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SheetMusicSearchRsquestDto {

    @NotBlank(message = "검색할 단어를 입력하세요")
    private String keyword;
}
