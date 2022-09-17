package com.example.sheetmusiclist.dto.sheetmusic;


import com.example.sheetmusiclist.entity.sheetmusic.SheetMusic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class SheetMusicFindAllResponseDto {


    private String title;
    private String songwriter;

    public static SheetMusicFindAllResponseDto toDto(SheetMusic sheetMusic) {
        return new SheetMusicFindAllResponseDto(sheetMusic.getTitle(), sheetMusic.getSongwriter());
    }

}
