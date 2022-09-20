package com.example.sheetmusiclist.dto.sheetmusic;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SheetMusicSearchRequestDto {

    @NotNull(message = "검색어를 입력해주세요.")
    private String searchKeyWord;

}
