package com.example.sheetmusiclist.dto.sheetmusic;


import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SheetMusicSearchRequestDto {

    @ApiModelProperty(value = "검색", notes = "검색어 입력해주세요.", required = true, example = "아이유")
    @NotNull(message = "검색어를 입력해주세요.")
    private String searchKeyWord;

}
