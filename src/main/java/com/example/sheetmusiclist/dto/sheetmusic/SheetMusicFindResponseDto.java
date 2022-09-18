package com.example.sheetmusiclist.dto.sheetmusic;


import com.example.sheetmusiclist.entity.sheetmusic.SheetMusic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SheetMusicFindResponseDto {

    private String title;

    private String songwriter;

    private String loginUser;

    // + 이미지


    public static SheetMusicFindResponseDto toDto(SheetMusic sheetMusic) {
        return new SheetMusicFindResponseDto(sheetMusic.getTitle(), sheetMusic.getWriter(),
                sheetMusic.getMember().getNickname());
    }
}
