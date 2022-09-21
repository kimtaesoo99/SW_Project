package com.example.sheetmusiclist.dto.sheetmusic;


import com.example.sheetmusiclist.entity.sheetmusic.SheetMusic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SheetMusicFindResponseDto {

    private String title;

    private String songwriter;

    private String loginUser;

    private List<ImageDto> images;


    public static SheetMusicFindResponseDto toDto(SheetMusic sheetMusic) {
        return new SheetMusicFindResponseDto(sheetMusic.getTitle(), sheetMusic.getWriter(),
                sheetMusic.getMember().getNickname(),sheetMusic.getImages().stream().map(i->ImageDto.toDto(i)).collect(Collectors.toList()));
    }
}
