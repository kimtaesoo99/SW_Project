package com.example.sheetmusiclist.dto.review;


import com.example.sheetmusiclist.entity.review.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class findReviewResponseDto {

    private String user;
    private String comment;
    private Integer rate;

    public static  findReviewResponseDto toDto(Review review) {
        return new findReviewResponseDto(review.getMember().getNickname(),
                review.getComment(), review.getRate());
    }
}
