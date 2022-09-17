package com.example.sheetmusiclist.dto.sheetmusic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SheetMusicEditRequestDto {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "작곡가를 입력해주세요")
    private String songwriter;

}
