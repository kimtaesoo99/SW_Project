package com.example.sheetmusiclist.dto.sheetmusic;


import com.example.sheetmusiclist.entity.sheetmusic.SheetMusic;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class SheetMusicFindAllResponseDto {

    @ApiModelProperty(notes = "악보  제목", example = "노래")
    private String title;

    @ApiModelProperty(notes = "악보 작곡가", example = "아이유")
    private String writer;

    public static SheetMusicFindAllResponseDto toDto(SheetMusic sheetMusic) {
        return new SheetMusicFindAllResponseDto(sheetMusic.getTitle(), sheetMusic.getWriter());
    }

}
