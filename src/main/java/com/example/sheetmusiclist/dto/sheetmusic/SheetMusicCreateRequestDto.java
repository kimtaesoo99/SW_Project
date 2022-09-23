package com.example.sheetmusiclist.dto.sheetmusic;

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

    @ApiParam(value = "악보 제목", required = true, example = "스물셋")
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @ApiParam(value = "악보 작곡가", required = true, example = "아이유")
    @NotBlank(message = "작곡가를 입력해주세요.")
    private String writer;

    @ApiParam(value = "이미지", required = true, example = "images")
    private List<MultipartFile> images = new ArrayList<>();

}
