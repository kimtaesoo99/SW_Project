package com.example.sheetmusiclist.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EditReviewRequestDto {

    @NotBlank(message = "리뷰를 작성해주세요.")
    private String comment;

    @NotNull(message = "평점을 입력해주세요.")
    private Integer rate;
}
