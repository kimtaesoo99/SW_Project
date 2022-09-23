package com.example.sheetmusiclist.dto.sheetmusic;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class SheetMusicCreateRequestDto {
    @ApiModelProperty(value = "제목", notes = "제목을 입력해주세요.", required = true, example = "하나둘")
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @ApiModelProperty(value = "작곡가", notes = "작곡가를 입력해주세요.", required = true, example = "김승민")
    @NotBlank(message = "작곡가를 입력해주세요.")
    private String writer;

    @ApiModelProperty(value = "이미지", notes = "이미지를 넣어주세요.", required = true, example = "김승민사진")
    private List<MultipartFile> images = new ArrayList<>();

}
