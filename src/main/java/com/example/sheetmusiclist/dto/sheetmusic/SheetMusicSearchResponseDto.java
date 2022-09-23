package com.example.sheetmusiclist.dto.sheetmusic;

import com.example.sheetmusiclist.entity.sheetmusic.SheetMusic;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class SheetMusicSearchResponseDto {
    @ApiModelProperty(notes = "악보 제목", example = "노래")
    private String title;

    @ApiModelProperty(notes = "악보 작곡가", example = "아이유")
    private String writer;

    public static SheetMusicSearchResponseDto toDto(SheetMusic sheetMusic){
        return new SheetMusicSearchResponseDto(sheetMusic.getTitle(),sheetMusic.getWriter());
    }
}
