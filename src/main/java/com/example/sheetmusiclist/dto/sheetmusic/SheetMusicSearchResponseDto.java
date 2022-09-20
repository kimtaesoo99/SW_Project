package com.example.sheetmusiclist.dto.sheetmusic;

import com.example.sheetmusiclist.entity.sheetmusic.SheetMusic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class SheetMusicSearchResponseDto {

    private String title;
    private String writer;

    public static SheetMusicSearchResponseDto toDto(SheetMusic sheetMusic){
        return new SheetMusicSearchResponseDto(sheetMusic.getTitle(),sheetMusic.getWriter());
    }
}
