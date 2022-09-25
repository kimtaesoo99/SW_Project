package com.example.sheetmusiclist.dto.sheetmusic;


import com.example.sheetmusiclist.entity.sheetmusic.SheetMusic;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(notes = "악보 제목", example = "노래")
    private String title;

    @ApiModelProperty(notes = "악보 작곡가", example = "아이유")
    private String writer;

    @ApiModelProperty(notes = "악보 등록자", example = "김철수")
    private String nickName;

    @ApiModelProperty(notes = "악보 이미지", example = "images")
    private List<ImageDto> images;


    public static SheetMusicFindResponseDto toDto(SheetMusic sheetMusic) {
        return new SheetMusicFindResponseDto(sheetMusic.getTitle(), sheetMusic.getWriter(),
                sheetMusic.getMember().getNickname(),sheetMusic.getPdfs().stream().map(i->ImageDto.toDto(i)).collect(Collectors.toList()));
    }
}
