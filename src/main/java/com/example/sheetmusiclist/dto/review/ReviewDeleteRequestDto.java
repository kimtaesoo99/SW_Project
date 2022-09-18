package com.example.sheetmusiclist.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewDeleteRequestDto {

    @NotNull(message = "악보 정보가 잘못되었습니다.")
    private Long sheetMusicId;
}
